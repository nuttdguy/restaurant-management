package com.restaurant.service;

import com.restaurant.domain.dto.enums.TokenType;
import com.restaurant.domain.model.*;
import com.restaurant.exception.NotFoundException;
import com.restaurant.repository.ITokenRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    // SERVICE UNDER TEST
    @InjectMocks
    TokenService tokenService;

    // MOCKS
    @Mock
    ITokenRepo tokenRepo;


    static final UUID userId = UUID.randomUUID();
    static final UUID tokenUuid = UUID.randomUUID();
    UniqueToken token = UniqueToken.builder()
            .id(1L)
            .token(tokenUuid)
            .tokenType(TokenType.REGISTRATION)
            .expiration(Instant.now())
            .user(User.builder()
                    .uuid(userId)
                    .username("alice@restaurant.com")
                    .password("password")
                    .build())
            .build();

    @AfterEach
    void tearDown() {
        tokenRepo.deleteAll();
    }

    @Test
    void testFindByToken() {
        // given
        when(tokenRepo.findByToken(tokenUuid)).thenReturn(token);

        // when
        UniqueToken expected = tokenService.findByToken(tokenUuid);

        // then
        assertNotNull(expected);
        assertEquals(1L, expected.getId());
        assertEquals(tokenUuid, expected.getToken());

        verify(tokenRepo).findByToken(tokenUuid);
    }

    @Test
    void testFindByTokenNotFoundException() {
        // given
        when(tokenRepo.findByToken(isNull())).thenThrow(NotFoundException.class);

        // when
        Exception exception = assertThrows(NotFoundException.class, () -> tokenService.findByToken(isNull()));

        // then
        assertEquals(NotFoundException.class, exception.getClass());
        verify(tokenRepo).findByToken(isNull());

    }

    @Test
    void testSave() {
        // given
        when(tokenRepo.save(any(UniqueToken.class))).thenReturn(token);

        // when
        UniqueToken uniqueToken = tokenService.save(token);

        // then
        assertNotNull(uniqueToken.getId());
        assertEquals(token.getToken(), uniqueToken.getToken());

        verify(tokenRepo).save(any(UniqueToken.class));
    }


    @Test
    void testDeleteByToken() {
        // when
        tokenService.deleteByToken(token);
        // then
        verify(tokenRepo).delete(token);
    }

}

