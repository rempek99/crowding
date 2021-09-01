package pl.remplewicz.crowding.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAccountDto {
    private long id;

    private String username;

    private String password;

    private boolean active;
}
