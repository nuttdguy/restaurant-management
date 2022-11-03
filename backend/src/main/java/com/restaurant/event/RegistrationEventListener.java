package com.restaurant.event;

import com.restaurant.domain.model.User;
import com.restaurant.email.Email;
import com.restaurant.email.EmailCredential;
import com.restaurant.email.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.Arrays;

@Slf4j
@Async
@Component
@AllArgsConstructor
public class RegistrationEventListener implements ApplicationListener<RegistrationEvent> {

    private final EmailService emailService;

    @Async
    @Override
    public void onApplicationEvent(RegistrationEvent event) {
        log.trace("onApplicationEvent - generating and saving the registration token");

        try {

            log.trace("Building the email");
            User user = (User) event.getSource();
            String url = event.getVerifyURL();

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