package com.restaurant.service;

import com.restaurant.domain.model.RegistrationToken;
import com.restaurant.exception.TokenNotFoundException;
import com.restaurant.repository.IRegistrationTokenRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class TokenService {

    private IRegistrationTokenRepo registerTokenRepo;

    public RegistrationToken findRegisterTokenById(String uuid) {
        return registerTokenRepo.findById(UUID.fromString(uuid))
                .orElseThrow(
                    () -> new TokenNotFoundException(format("Token %s not found", uuid)));
    }

    public int deleteRegisterToken(RegistrationToken token) {
        registerTokenRepo.delete(token);
        return 1;
    }

}
