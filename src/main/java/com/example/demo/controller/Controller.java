package com.example.demo.controller;

import com.example.demo.dto.response.TicketResponseDto;
import com.example.demo.entity.User;
import com.example.demo.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/controller")
public class Controller {

    private final TicketService ticketService;


    @GetMapping("/next/{iin}")
    public ResponseEntity<TicketResponseDto> generateTicket(@PathVariable String iin) {
        return ResponseEntity.ok(ticketService.generateTicket(iin));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<TicketResponseDto>> generateTicket() {
        return ResponseEntity.ok(ticketService.getAllWaiting());
    }

    @GetMapping("/getManagerTicket")
    public ResponseEntity<TicketResponseDto> getManagerTicket(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ticketService.getManagerTicket(user));
    }

    @GetMapping("/changeTicketStatus")
    public ResponseEntity changeTicketStatus(@AuthenticationPrincipal User user) {
        ticketService.changeStatus(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
