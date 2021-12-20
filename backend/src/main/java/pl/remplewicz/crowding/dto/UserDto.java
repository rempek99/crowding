package pl.remplewicz.crowding.dto;

import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * Created by:
 * Name: Arkadiusz Remplewicz
 * Index Number: 224413
 * E-mail: arkadiusz.remplewicz@gmail.com
 * Git-Hub Username: rempek99
 */

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
