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

    public static User createEntityFromDto(UserDto userDto) {
        return new User(userDto.getUsername(), userDto.getPassword());
    }
}
