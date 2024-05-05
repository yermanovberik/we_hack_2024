package com.example.demo.security.auth;


import com.example.demo.entity.User;
import com.example.demo.enums.Role;
import com.example.demo.exception.UserAlreadyExistAuthenticationException;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.config.JwtService;
import com.example.demo.security.token.Token;
import com.example.demo.security.token.TokenRepository;
import com.example.demo.security.token.TokenType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse registerUser(RegisterRequest request) {
        return register(request, Role.USER);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    public AuthenticationResponse registerModerator(RegisterRequest request) {
        return register(request, Role.MODERATOR);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    public AuthenticationResponse registerAdmin(RegisterRequest request) {
        return register(request, Role.ADMIN);
    }

    @Transactional
    public AuthenticationResponse register(RegisterRequest request, Role role) {
        if(repository.findByIin(request.getIin()).isPresent()) throw new UserAlreadyExistAuthenticationException("User is already exists with this email");
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .iin(request.getIin())
                .phoneNumber(request.getPhoneNumber())
                .priority("1")
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        var savedUser = repository.save(user);
        var jwtAccessToken = jwtService.genarateAccessToken(user);


        saveUserToken(savedUser, jwtAccessToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtAccessToken)
                .build();
    }

    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        request.getIin(), request.getPassword())
        );
        var user = repository.findByIin(request.getIin()).orElseThrow();
        var jwtAccessToken = jwtService.genarateAccessToken(user);


        revokeAllUserTokens(user);
        saveUserToken(user, jwtAccessToken);
        return AuthenticationResponse.builder().accessToken(jwtAccessToken).build();
    }

    private void revokeAllUserTokens(User user){
        var validUserTokens = tokenRepository.findAllValidTokensByUser(Math.toIntExact(user.getId()));
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(t->{
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
}
