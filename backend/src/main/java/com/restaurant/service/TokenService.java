package com.restaurant.service;

import com.restaurant.domain.model.UniqueToken;
import com.restaurant.exception.DataIntegrityException;
import com.restaurant.repository.ITokenRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class TokenService {

    private ITokenRepo tokenRepo;

    public Optional<UniqueToken> findByToken(UUID token) {
        return tokenRepo.findByToken(token);
    }

    public void deleteByToken(UniqueToken token) {
        try {
            tokenRepo.delete(token);
        } catch (DataIntegrityViolationException ex) {
            log.error(ex.getLocalizedMessage());
            throw new DataIntegrityException(ex.getLocalizedMessage());
        }

    }

    public void save(UniqueToken uniqueToken) {
        try {
            tokenRepo.save(uniqueToken);
        } catch (DataIntegrityViolationException ex) {
            log.error(ex.getLocalizedMessage());
            throw new DataIntegrityException(ex.getLocalizedMessage());
        }
    }
}
