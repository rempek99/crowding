package pl.remplewicz.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

    public enum Gender{
        MALE, FEMALE
    }


    private String username;
    private String firstname;
    private String surname;
    private Gender gender;
    private Integer age;
}
