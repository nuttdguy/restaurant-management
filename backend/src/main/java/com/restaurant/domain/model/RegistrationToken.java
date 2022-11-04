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
@Builder
public class RegistrationToken  {

    @Value("${restaurant.jwt.registrationExpirationMS}")
    public static Integer EXPIRATION_TIME_MS = 360000;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "token")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID token;

    @Column(name = "expiration")
    private Instant expiration;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_uuid",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_REGISTRATION_TOKEN_USER"))
    public User user;

    public RegistrationToken(User user, UUID token) {
        this.user = user;
        this.token = token;
        this.expiration = Instant.now().plusMillis(EXPIRATION_TIME_MS);
    }

    public void setExpiration(Instant instant) {
        this.expiration = instant.plusMillis(EXPIRATION_TIME_MS);
    }


}
