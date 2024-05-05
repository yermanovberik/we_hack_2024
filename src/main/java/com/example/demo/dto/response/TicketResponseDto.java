package com.example.demo.dto.response;

import com.example.demo.entity.Window;
import com.example.demo.enums.Priority;
import com.example.demo.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketResponseDto {

    private Long id;

    private Long ticketNumber;

    private Status status;

    private Priority priority;

    private LocalDateTime dateTime;

    private Window window;

}
