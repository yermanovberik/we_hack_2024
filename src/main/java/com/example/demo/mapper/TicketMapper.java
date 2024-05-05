package com.example.demo.mapper;

import com.example.demo.dto.response.TicketResponseDto;
import com.example.demo.dto.response.UserDto;
import com.example.demo.entity.Ticket;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketMapper extends Mappable<Ticket, TicketResponseDto>{
}
