package pl.remplewicz.crowding.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
public class UserDto {

    @NotEmpty
    @Size(min = 3, max = 16)
    private String username;

    @NotEmpty
    @Size(min=8)
    private String password;
}
