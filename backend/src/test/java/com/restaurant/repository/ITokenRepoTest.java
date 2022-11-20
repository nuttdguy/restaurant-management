package com.restaurant.repository;

import com.restaurant.domain.model.UniqueToken;
import com.restaurant.domain.model.User;
import com.restaurant.domain.dto.enums.TokenType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.AfterEach;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class ITokenRepoTest {

    @Autowired
    ITokenRepo tokenRepo;

    @AfterEach
    void tearDown() {
        tokenRepo.deleteAll();
    }

    // setup
    static final UUID userId = UUID.randomUUID();
    static final UUID tokenValueUnderTest = UUID.randomUUID();
    UniqueToken tokenUnderTest = UniqueToken.builder()
            .id(1L)
            .token(tokenValueUnderTest)
            .tokenType(TokenType.REGISTRATION)
            .expiration(Instant.now())
            .user(User.builder()
                    .uuid(userId)
                    .username("alice@restaurant.com")
                    .password("password")
                    .build())
            .build();

    @Test
    void shouldFindUniqueTokenByToken() {
        // given
        tokenRepo.save(tokenUnderTest);
        // when
        Optional<UniqueToken> expected = tokenRepo.findByToken(tokenUnderTest.getToken());
        // then
        assertThat(expected).isNotEmpty();
        assertThat(expected.get().getToken()).isEqualTo(tokenValueUnderTest);
    }


}

