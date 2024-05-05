package com.example.demo.service;

import com.example.demo.dto.response.TicketResponseDto;
import com.example.demo.entity.User;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface TicketService{

    TicketResponseDto generateTicket(String iin);

    List<TicketResponseDto> getAllWaiting();

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    TicketResponseDto getManagerTicket(User user);

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    void changeStatus(User user);

}
