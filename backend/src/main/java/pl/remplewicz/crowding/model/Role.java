package pl.remplewicz.crowding.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Objects;

/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * Created by:
 * Name: Arkadiusz Remplewicz
 * Index Number: 224413
 * E-mail: arkadiusz.remplewicz@gmail.com
 * Git-Hub Username: rempek99
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "user_authorities")
public class Role implements GrantedAuthority {

    // Statics
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";


    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "varchar(128)")
    private String authority;

    private boolean enabled = false;


    //  Methods
    public Role(User user, String authority, boolean enabled) {
        this.user = user;
        this.authority = authority;
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return id != null && Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
