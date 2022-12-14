package com.restaurant.domain.model;

import com.restaurant.domain.dto.enums.TokenType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tokens")
@ToString
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UniqueToken {

    @Value("${restaurant.jwt.registrationExpirationMS}")
    public static Integer EXPIRATION_TIME_MS = 360000;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "token", unique = true)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID token;

    @Column(name = "token_type")
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    @Column(name = "expiration")
    private Instant expiration;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_uuid")
    @ToString.Exclude
    private User user;

    public void setExpiration(Instant instant) {
        this.expiration = instant.plusMillis(EXPIRATION_TIME_MS);
    }


    public UniqueToken(User user, UUID token, TokenType tokenType) {
        this.user = user;
        this.token = token;
        this.tokenType = tokenType;
        this.expiration = Instant.now().plusMillis(EXPIRATION_TIME_MS);
    }

}
