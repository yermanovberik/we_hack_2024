package com.example.demo.service.impl;

import com.example.demo.dto.response.TicketResponseDto;
import com.example.demo.entity.Ticket;
import com.example.demo.entity.User;
import com.example.demo.entity.Window;
import com.example.demo.enums.Priority;
import com.example.demo.enums.Status;
import com.example.demo.mapper.TicketMapper;
import com.example.demo.rabbitmq.RabbitMQConsumer;
import com.example.demo.rabbitmq.RabbitMQProducer;
import com.example.demo.repository.TicketRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WindowRepository;
import com.example.demo.service.TicketService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final RabbitMQProducer producer;
    private final RabbitMQConsumer consumer;
    private final WindowRepository windowRepository;
    private final UserRepository userRepository;
    private final TicketMapper ticketMapper;



    @Override
    public synchronized TicketResponseDto generateTicket(String iin) {
        Ticket ticket = new Ticket();
        ticket.setIin(iin);
        ticket.setPriority(Priority.FIRST);
        ticket.setStatus(Status.WAITING);
        ticketRepository.save(ticket);

        producer.sendMessage(ticket.getId()+"");

        return ticketMapper.toDto(ticket);

    }

    @Override
    public List<TicketResponseDto> getAllWaiting() {
        List<Ticket> ticketList = ticketRepository.findByStatus(Status.IN_PROGRESS);
        return ticketMapper.toDtoList(ticketList);
    }



    @Override
    public synchronized TicketResponseDto getManagerTicket(User user) {

        Window window = windowRepository.findById(user.getWindowId())
                .orElseThrow(() -> new EntityNotFoundException("Window not fount with id"));
        window.setIsAvailable(false);
        windowRepository.save(window);

        String ticketId = consumer.getMessageFromQueue();

        Ticket ticket = ticketRepository.findById(Long.parseLong(ticketId))
                .orElseThrow(() -> new EntityNotFoundException("Immovable not fount with id: " + ticketId));
        ticket.setStatus(Status.IN_PROGRESS);
        ticket.setWindow(window);
        ticketRepository.save(ticket);

        user.setManagerTicketId(Long.valueOf(ticketId));
        userRepository.save(user);
        return ticketMapper.toDto(ticket);

    }
    @Override
    public synchronized void changeStatus(User user) {
        Long ticketId = user.getManagerTicketId();
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Immovable not fount with id: " + ticketId));
        ticket.setStatus(Status.CONFIRMED);
        Window window = ticket.getWindow();
        window.setIsAvailable(true);
        windowRepository.save(window);
    }

}
