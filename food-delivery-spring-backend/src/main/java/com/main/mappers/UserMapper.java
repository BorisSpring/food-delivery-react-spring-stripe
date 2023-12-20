package com.main.mappers;

import com.main.dto.UserDto;
import com.main.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {

    @Mapping(source = "authority.name", target="authority")
    @Mapping(source = "imageName", target = "imageName")
    UserDto userToUserDto(User user);
}
