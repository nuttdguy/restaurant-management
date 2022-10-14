package com.restaurant.event;

import com.restaurant.domain.model.RegistrationToken;
import com.restaurant.domain.model.User;
import com.restaurant.email.Email;
import com.restaurant.email.EmailCredential;
import com.restaurant.email.EmailService;
import com.restaurant.repository.IRegistrationTokenRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.Arrays;
import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class RegistrationEventListener implements ApplicationListener<RegistrationEvent> {

    private final EmailService emailService;
    private final IRegistrationTokenRepo tokenRepo;

    @Async
    @Override
    @Transactional
    public void onApplicationEvent(RegistrationEvent event) {
        log.trace("onApplicationEvent - generating and saving the registration token");

        // create verification token for user with link
        User user = (User) event.getSource();
        UUID token = UUID.randomUUID();
        String url = event.getEvent_url() + token.toString();

        // save the registration token
        RegistrationToken registrationToken = new RegistrationToken(user, token);
        log.trace("Saving the registration token {}", registrationToken.getExpiration());
        tokenRepo.save(registrationToken);

        try {

            log.trace("Building the email");

            Email email = Email.builder.setTo(EmailCredential.TO)
                    .setFrom(EmailCredential.FROM)
                    .withSubject(EmailCredential.SUBJECT)
                    .withText(EmailCredential.TEXT)
                    .withModel(emailService
                            .buildTemplateModel(user, "", url, 10L))
                    .build();

            log.trace("Sending the email");
            String template = "activate-account-email-template.ftlh";
            emailService.sendEmailWithTemplate(email, template);

            log.info("Logging the link to verify registration {}", url);

        } catch (MessagingException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }

    }

}