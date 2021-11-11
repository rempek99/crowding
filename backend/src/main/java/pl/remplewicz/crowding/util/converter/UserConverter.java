package pl.remplewicz.crowding.util.converter;

import pl.remplewicz.crowding.dto.UserDto;
import pl.remplewicz.crowding.model.User;

public class UserConverter {

    private UserConverter(){}

    public static UserDto toDto(User entity){
        return UserDto.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .build();
    }
}
