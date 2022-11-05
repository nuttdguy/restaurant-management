package com.restaurant.service;

import com.restaurant.domain.model.UuidToken;
import com.restaurant.repository.ITokenRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class TokenService {

    private ITokenRepo tokenRepo;

    public Optional<UuidToken> findToken(UUID token) {
        return tokenRepo.findByToken(token);
    }

    public void deleteToken(UuidToken token) {
        tokenRepo.delete(token);
    }

    public UuidToken save(UuidToken uuidToken) {
        return tokenRepo.save(uuidToken);
    }
}
