package com.restaurant.event;

import com.restaurant.domain.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;
import java.util.UUID;


@Getter
@Setter
public class RegistrationEvent extends ApplicationEvent {

    private final String verifyURL;

    public RegistrationEvent(User user, String verifyURL, UUID token) {
        super(user, Clock.systemUTC());
        this.verifyURL = verifyURL + "/" + token;
    }
}
