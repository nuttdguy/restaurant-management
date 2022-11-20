package com.restaurant.service;

import com.restaurant.domain.dto.enums.PhotoType;
import com.restaurant.domain.dto.enums.TokenType;
import com.restaurant.domain.model.*;
import com.restaurant.repository.ITokenRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @InjectMocks
    TokenService tokenService;


    @Mock
    ITokenRepo tokenRepo;

    @Mock
    Logger log;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByToken() {
        when(tokenRepo.findByToken(any())).thenReturn(null);

        Optional<UniqueToken> result = tokenService.findByToken(null);
        Assertions.assertEquals(null, result);
    }

    @Test
    void testDeleteByToken() {
        tokenService.deleteByToken(new UniqueToken(Long.valueOf(1), null, TokenType.REGISTRATION, LocalDateTime.of(2022, Month.NOVEMBER, 20, 9, 45, 48).toInstant(ZoneOffset.UTC), new User(null, "username", "password", "firstName", "lastName", LocalDateTime.of(2022, Month.NOVEMBER, 20, 9, 45, 48), LocalDateTime.of(2022, Month.NOVEMBER, 20, 9, 45, 48), Set.of(new Role(Long.valueOf(1), "name", Set.of(null))), Set.of(new Restaurant(null, "name", "alias", "url", "phone", "category", "description", "address1", "address2", "city", "state", "zip", "country", Boolean.TRUE, Boolean.TRUE, LocalDateTime.of(2022, Month.NOVEMBER, 20, 9, 45, 48), LocalDateTime.of(2022, Month.NOVEMBER, 20, 9, 45, 48), new License(Long.valueOf(1), "name", "type", new byte[]{(byte) 0}, "fileUrl", null), null, Set.of(new Photo(Long.valueOf(1), "name", "type", new byte[]{(byte) 0}, PhotoType.PROFILE, "photoUrl", null, new Dish(Long.valueOf(1), "name", "category", "tags", "description", "ingredients", new BigDecimal(0), null, Set.of(null)))), Set.of(new Dish(Long.valueOf(1), "name", "category", "tags", "description", "ingredients", new BigDecimal(0), null, Set.of(new Photo(Long.valueOf(1), "name", "type", new byte[]{(byte) 0}, PhotoType.PROFILE, "photoUrl", null, null)))))), Set.of(null), true)));
    }

    @Test
    void testSave() {
        tokenService.save(new UniqueToken(Long.valueOf(1), null, TokenType.REGISTRATION, LocalDateTime.of(2022, Month.NOVEMBER, 20, 9, 45, 48).toInstant(ZoneOffset.UTC), new User(null, "username", "password", "firstName", "lastName", LocalDateTime.of(2022, Month.NOVEMBER, 20, 9, 45, 48), LocalDateTime.of(2022, Month.NOVEMBER, 20, 9, 45, 48), Set.of(new Role(Long.valueOf(1), "name", Set.of(null))), Set.of(new Restaurant(null, "name", "alias", "url", "phone", "category", "description", "address1", "address2", "city", "state", "zip", "country", Boolean.TRUE, Boolean.TRUE, LocalDateTime.of(2022, Month.NOVEMBER, 20, 9, 45, 48), LocalDateTime.of(2022, Month.NOVEMBER, 20, 9, 45, 48), new License(Long.valueOf(1), "name", "type", new byte[]{(byte) 0}, "fileUrl", null), null, Set.of(new Photo(Long.valueOf(1), "name", "type", new byte[]{(byte) 0}, PhotoType.PROFILE, "photoUrl", null, new Dish(Long.valueOf(1), "name", "category", "tags", "description", "ingredients", new BigDecimal(0), null, Set.of(null)))), Set.of(new Dish(Long.valueOf(1), "name", "category", "tags", "description", "ingredients", new BigDecimal(0), null, Set.of(new Photo(Long.valueOf(1), "name", "type", new byte[]{(byte) 0}, PhotoType.PROFILE, "photoUrl", null, null)))))), Set.of(null), true)));
    }
}

