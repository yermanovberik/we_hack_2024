package com.example.demo.mapper;

import com.example.demo.dto.response.UserDto;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserDto>{
    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}
