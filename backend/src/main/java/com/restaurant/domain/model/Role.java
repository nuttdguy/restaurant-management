package com.restaurant.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    @JsonIgnore
    private Long id;

    @Column(length = 20, unique = true)
    @JsonIgnore
    private String name;

    // https://thorben-janssen.com/ultimate-guide-association-mappings-jpa-hibernate/
    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    @ToString.Exclude
    private Set<User> users = new HashSet<>();

    public Role(String roleType) {
        this.name = roleType;
    }

    @Override
    public String getAuthority() {
        return name;
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