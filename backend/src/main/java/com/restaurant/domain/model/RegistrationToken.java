package com.restaurant.domain.model;

import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@ToString
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationToken  {

    @Value("${restaurant.jwt.registrationExpirationMS}")
    public static Integer EXPIRATION_TIME_MS = 360000;

    @Id
    @Column(name = "token")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID uuid;

    @Column(name = "expiration")
    private Instant expiration;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_uid",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_REGISTRATION_TOKEN_USER"))
    public User user;

    public RegistrationToken(User user, UUID token) {
        this.user = user;
        this.uuid = token;
        this.expiration = Instant.now().plusMillis(EXPIRATION_TIME_MS);
    }

}
