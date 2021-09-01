package pl.remplewicz.crowding.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, length = 64)
    private String username;

    @Column(length = 64)
    private String password;

    private boolean active = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserAccount that = (UserAccount) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 76039858;
    }
}
