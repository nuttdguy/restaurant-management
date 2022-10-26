package com.restaurant.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users",
        uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(name = "user_uuid")
    private UUID uuid;

    @Column(name = "username")
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

    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = { @JoinColumn(name = "user_uuid", referencedColumnName = "user_uuid")},
            inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "role_id") })
    @ToString.Exclude
    @JsonIgnore
    private Set<Role> authorities = new HashSet<>();


    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.TRUE)
    @ToString.Exclude
    private Set<Restaurant> restaurants = new HashSet<>();


    public User(UUID uuid, String username, String password, Set<Role> authorities) {
        this.uuid = uuid;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public static User build(User user) {
        Set<Role> authorities = user.getAuthorities().stream()
                .map(role -> new Role(role.getAuthority()))
                .collect(Collectors.toSet());

        return new User(
                user.getUuid(),
                user.getUsername(),
                user.getPassword(),
                authorities);
    }

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
