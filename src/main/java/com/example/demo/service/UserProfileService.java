package com.example.demo.service;


import com.example.demo.dto.request.PasswordEditRequest;
import com.example.demo.dto.response.UserDto;
import com.example.demo.entity.User;
import com.example.demo.exception.PasswordMismatchException;
import com.example.demo.exception.UserNotFoundException;

public interface UserProfileService {
    UserDto getCurrentUserDetails(User user);

    UserDto getUserDetailsById(Long id) throws UserNotFoundException;

    UserDto updateUserProfile(UserDto userDto, User user);

    void deleteUserProfile(User user);

    void editPassword(PasswordEditRequest request, User user) throws PasswordMismatchException;

    UserDto getUserDetailsByLogin(String login);
}
