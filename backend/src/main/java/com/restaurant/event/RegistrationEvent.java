package com.restaurant.event;

import com.restaurant.domain.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;


@Getter
@Setter
public class RegistrationEvent extends ApplicationEvent {

    private final String event_url;

    public RegistrationEvent(User user, String event_url) {
        super(user, Clock.systemUTC());
        this.event_url = event_url;
    }
}
