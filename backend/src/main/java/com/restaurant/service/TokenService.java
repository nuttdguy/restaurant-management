package com.restaurant.service;

import com.restaurant.domain.model.UniqueToken;
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

    public Optional<UniqueToken> findByToken(UUID token) {
        return tokenRepo.findByToken(token);
    }

    public void deleteByToken(UniqueToken token) {
        tokenRepo.delete(token);
    }

    public UniqueToken save(UniqueToken uniqueToken) {
        return tokenRepo.save(uniqueToken);
    }
}
