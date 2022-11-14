package com.restaurant.event;

import com.restaurant.domain.dto.enums.TokenType;
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
            String urlLink = event.getUrlLink();
            String tokenType = event.getTokenType();
            String subject = "";
            String template = "";
            if (tokenType.equals(TokenType.REGISTRATION.toString())) {
                template = "activate-account-email-template.ftlh";
                subject = "Confirm email";
            }
            if (tokenType.equals(TokenType.PASSWORD_RESET.toString())) {
                template = "reset-password-template.ftlh";
                subject = "Password reset";
            }

            Email email = Email.builder
                    .setTo(EmailCredential.TO)
                    .setFrom(EmailCredential.FROM)
                    .withSubject(subject)
                    .withText(EmailCredential.TEXT)
                    .withModel(emailService.buildTemplateModel(user, "", urlLink, 10L))
                    .build();

            log.trace("Sending the email");
            log.info("Logging the url link {}", urlLink);
            emailService.sendEmailWithTemplate(email, template);

        } catch (MessagingException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }

    }

}