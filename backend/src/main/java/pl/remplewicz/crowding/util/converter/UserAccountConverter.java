package pl.remplewicz.crowding.util.converter;


import java.util.List;

import pl.remplewicz.crowding.dto.UserAccountDto;
import pl.remplewicz.crowding.model.User;
import java.util.stream.Collectors;

public class UserAccountConverter {

    private UserAccountConverter() {

    }

    public static UserAccountDto entityToDto(User user) {
        return UserAccountDto
                .builder()
                .id(user.getId())
                .password(user.getPassword())
                .username(user.getUsername())
                .active(user.isEnabled())
                .build();
    }

    public static List<UserAccountDto> entityListToDtoList(List<User> userList) {
        return userList
                .stream()
                .map(UserAccountConverter::entityToDto)
                .collect(Collectors.toList());
    }

    public static User createEntityFromDto(UserAccountDto userAccount) {
        return new User(
                userAccount.getId(),
                userAccount.getUsername(),
                userAccount.getPassword(),
                userAccount.isActive()
        );
    }
}
