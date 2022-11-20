package com.restaurant.service;

import com.restaurant.domain.model.UniqueToken;
import com.restaurant.exception.DataIntegrityException;
import com.restaurant.exception.NotFoundException;
import com.restaurant.repository.ITokenRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.restaurant.exception.ExceptionMessage.NOT_FOUND;
import static java.lang.String.format;

@Slf4j
@Service
@AllArgsConstructor
public class TokenService {

    private ITokenRepo tokenRepo;

    public UniqueToken findByToken(UUID token) {
        return Optional.of(tokenRepo.findByToken(token))
                .orElseThrow(() -> new NotFoundException(format(NOT_FOUND, token)));
    }


    public UniqueToken save(UniqueToken uniqueToken) {
        try {
            return tokenRepo.save(uniqueToken);
        } catch (IllegalArgumentException  ex) {
            log.error(ex.getLocalizedMessage());
            throw new IllegalArgumentException (ex.getLocalizedMessage());
        }
    }


    public void deleteByToken(UniqueToken token) {
        tokenRepo.delete(token);
    }

}
