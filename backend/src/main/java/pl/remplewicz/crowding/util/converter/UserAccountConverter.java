package pl.remplewicz.crowding.util.converter;


import java.util.List;

import pl.remplewicz.crowding.dto.UserAccountDto;
import pl.remplewicz.crowding.model.UserAccount;
import java.util.stream.Collectors;

public class UserAccountConverter {

    private UserAccountConverter() {

    }

    public static UserAccountDto entityToDto(UserAccount userAccount) {
        return UserAccountDto
                .builder()
                .id(userAccount.getId())
                .password(userAccount.getPassword())
                .username(userAccount.getUsername())
                .active(userAccount.isActive())
                .build();
    }

    public static List<UserAccountDto> entityListToDtoList(List<UserAccount> userAccountList) {
        return userAccountList
                .stream()
                .map(UserAccountConverter::entityToDto)
                .collect(Collectors.toList());
    }

    public static UserAccount createEntityFromDto(UserAccountDto userAccount) {
        return new UserAccount(
                userAccount.getId(),
                userAccount.getUsername(),
                userAccount.getPassword(),
                userAccount.isActive()
        );
    }
}
