package com.restaurant.domain.model;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "registration_token")
@ToString
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationToken extends Token {

    @Value("${restaurant.jwt.registrationExpirationMS}")
    public static Integer EXPIRATION_TIME_MS = 360000;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "registration_token_token", nullable = false,
//            foreignKey = @ForeignKey(name = "FK_REGISTRATION_TOKEN_USER"))
    public User user;

    public RegistrationToken(User user, UUID token) {
        super(token, EXPIRATION_TIME_MS);
        this.user = user;
    }

    @Override
    public void setExpiration() {
        expiration = Instant.now().plusSeconds(EXPIRATION_TIME_MS);
    }

    @Override
    public Instant getExpiration() {
        return expiration;
    }

}
