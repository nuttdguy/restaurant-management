package com.restaurant.event;

import com.restaurant.domain.model.TokenType;
import com.restaurant.domain.model.User;
import com.restaurant.email.Email;
import com.restaurant.email.EmailCredential;
import com.restaurant.email.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.Arrays;

@Slf4j
@Component
@AllArgsConstructor
public class SendEmailEventListener implements ApplicationListener<SendEmailEvent> {

    private final EmailService emailService;

    @Override
    public void onApplicationEvent(SendEmailEvent event) {
        log.trace("onApplicationEvent - sending the event email");

        try {

            log.trace("Building the email");
            User user = (User) event.getSource();
            String url = event.getUrlLink();
            String tokenType = event.getTokenType();

            Email email = Email.builder.setTo(EmailCredential.TO)
                    .setFrom(EmailCredential.FROM)
                    .withSubject(EmailCredential.SUBJECT)
                    .withText(EmailCredential.TEXT)
                    .withModel(emailService
                            .buildTemplateModel(user, "", url, 10L))
                    .build();

            log.trace("Sending the email");
            String template = "";
            if (tokenType.equals(TokenType.REGISTRATION.toString())) {
                template = "activate-account-email-template.ftlh";
            }
            if (tokenType.equals(TokenType.PASSWORD_RESET.toString())) {
                template = "reset-password-template.ftlh";
            }
            emailService.sendEmailWithTemplate(email, template);

            log.info("Logging the url link {}", url);

        } catch (MessagingException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }

    }

}