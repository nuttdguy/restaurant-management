package com.restaurant.service;

import com.restaurant.domain.model.RegistrationToken;
import com.restaurant.exception.NotFoundException;
import com.restaurant.repository.IRegistrationTokenRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.restaurant.exception.ExceptionMessage.NOT_FOUND;
import static java.lang.String.format;

@Service
@AllArgsConstructor
public class TokenService {

    private IRegistrationTokenRepo registerTokenRepo;

    public Optional<RegistrationToken> findRegisterTokenById(UUID token) {
        return registerTokenRepo.findById(token);
    }

    public int deleteRegisterToken(RegistrationToken token) {
        registerTokenRepo.delete(token);
        return 1;
    }

    public RegistrationToken save(RegistrationToken registrationToken) {
        return registerTokenRepo.save(registrationToken);
    }
}
