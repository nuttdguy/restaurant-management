package com.restaurant.domain.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.Instant;
import java.util.UUID;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@MappedSuperclass
public abstract class Token {

    @Id
    @Column(name = "token")
    @Type(type = "org.hibernate.type.UUIDCharType")
    protected UUID uuid;

    @Column(name = "expiration")
    protected Instant expiration;

    Token(UUID token, Integer expiration) {
        this.uuid = token;
        this.expiration = Instant.now().plusMillis(expiration);
    }

    abstract void setExpiration();

    abstract Instant getExpiration();

}
