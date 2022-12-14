package com.restaurant.domain.model;

import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
@Builder
public class User implements UserDetails, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(name = "user_uuid")
    private UUID uuid;

    @Column(name = "userName", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "user_roles",
            joinColumns = { @JoinColumn(name = "user_uuid")},
            inverseJoinColumns = { @JoinColumn(name = "role_id") })
    private Set<Role> authorities = new HashSet<>();

    @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private  Set<Restaurant> restaurants = new HashSet<>();

    @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<UniqueToken> tokens = new HashSet<>();


    public void addRole(Role role) {
        if (this.authorities == null) {
            this.authorities = new HashSet<>();
        }
        this.authorities.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        this.authorities.remove(role);
        role.getUsers().remove(this);
    }

    public void addRestaurant(Restaurant restaurant) {
        this.restaurants.add(restaurant);
        restaurant.setUser(this);
    }

    public void removeRestaurant(Restaurant restaurant) {
        this.restaurants.remove(restaurant);
    }

    public void addToken(UniqueToken token) {
        this.tokens.add(token);
        token.setUser(this);
    }

    public void removeToken(UniqueToken token) {
        this.tokens.remove(token);
    }

//    public User(UUID uuid, String userName, String password, Set<Role> authorities) {
//        this.uuid = uuid;
//        this.userName = userName;
//        this.password = password;
//        this.authorities = authorities;
//    }

//    public static User build(User user) {
//        Set<Role> authorities = user.getAuthorities().stream()
//                .map(role -> new Role(role.getAuthority()))
//                .collect(Collectors.toSet());
//
//        return new User(
//                user.getUuid(),
//                user.getUsername(),
//                user.getPassword(),
//                authorities);
//    }

    @Column(name = "enabled")
    private boolean enabled = false;

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
}
