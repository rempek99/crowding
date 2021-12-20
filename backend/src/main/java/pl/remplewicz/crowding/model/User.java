package pl.remplewicz.crowding.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * Created by:
 * Name: Arkadiusz Remplewicz
 * Index Number: 224413
 * E-mail: arkadiusz.remplewicz@gmail.com
 * Git-Hub Username: rempek99
 */

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean enabled = true;

    @Column(unique = true)
    private String username;

    private String password;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private UserInfo userInfo;


    // Methods
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private final Set<Role> authorities = new HashSet<>();

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }


    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    public void addAuthority(String roleName) {
        addAuthority(roleName, true);
    }

    public void addAuthority(String roleName, boolean activated) {
        Predicate<Role> rolePredicate = role -> role.getAuthority().equals(roleName);
        if (authorities.stream().anyMatch(rolePredicate)) {
            authorities.stream().filter(rolePredicate).forEach(role -> role.setEnabled(activated));
        } else {
            authorities.add(new Role(this, roleName, activated));
        }
    }

    public void removeAuthority(String roleName) {
        Predicate<Role> rolePredicate = role -> role.getAuthority().equals(roleName);
        authorities.stream().filter(rolePredicate).forEach(role -> role.setEnabled(false));
    }

    public boolean hasRole(String roleName) {
        return authorities.stream().filter(role -> role.getAuthority().equals(roleName)).anyMatch(Role::isEnabled);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
