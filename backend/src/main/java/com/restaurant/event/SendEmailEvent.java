package com.restaurant.event;

import com.restaurant.domain.dto.enums.TokenType;
import com.restaurant.domain.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;
import java.util.UUID;


@Getter
@Setter
public class SendEmailEvent extends ApplicationEvent {

    private final String urlLink;
    private final String tokenType;

    public SendEmailEvent(User user, String urlLink, UUID token, TokenType tokenType) {
        super(user, Clock.systemUTC());
        this.urlLink = urlLink + "/" + token;
        this.tokenType = tokenType.toString();
    }
}
