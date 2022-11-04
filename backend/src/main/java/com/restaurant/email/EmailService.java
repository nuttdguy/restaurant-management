package com.restaurant.email;

import com.restaurant.domain.model.User;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final Configuration freeMarker;

    @Async("asyncExecutor1")
    public void sendSimpleEmail(Email theEmail) throws MessagingException {
        log.trace("EmailService - sendEmail");

        SimpleMailMessage simpleMailMessage = getSimpleMailMessage(theEmail);
        javaMailSender.send(simpleMailMessage);
    }

    @Async("asyncExecutor1")
    public void sendEmailWithAttachment(Email theEmail) throws MessagingException {
        log.trace("EmailService - sendEmailWithAttachment");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        configureHeader(mimeMessage);
        MimeMessageHelper helper = getMimeMessageHelper(theEmail, mimeMessage);

        // load files from path - implement method to handle more than one
        String path = "../some-path-to-file";
        FileSystemResource file = new FileSystemResource(path);
        helper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
        // OR
        // mimeMessageHelper.addAttachment("Name of attachment", new ClassPathResource("image.png"));

        log.trace("Sending the email message");
        javaMailSender.send(mimeMessage);
    }

    @Async("asyncExecutor1")
    public void sendEmailWithTemplate(Email theEmail, String template) throws MessagingException {
        log.trace("Async - EmailService - sendEmailWithTemplate");

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        configureHeader(mimeMessage);
        MimeMessageHelper message = getMimeMessageHelper(theEmail, mimeMessage);

        log.trace("Async - Converting email to text {}", theEmail);
        String emailContent;

        try {
            emailContent = getContentFromTemplate(theEmail.getTemplateModel(), template);
        } catch (TemplateException ex) {
            log.trace("Unable to parse template");
            log.trace(ex.getLocalizedMessage());
            return;
        } catch (IOException io) {
            log.trace("IO exception");
            log.trace(io.getLocalizedMessage());
            return;
        }

        // set the text as html
        message.setText(emailContent, true);

        log.trace("Async - Sending the email message");
        javaMailSender.send(message.getMimeMessage());
    }

    private void configureHeader(MimeMessage mimeMessage) throws MessagingException {
        mimeMessage.addHeader("Content-type", "text/HTML; charset=UTF-8");
        mimeMessage.addHeader("format", "flowed");
        mimeMessage.addHeader("Content-Transfer-Encoding", "8bit");
    }

    // inject and create template from model
    private String getContentFromTemplate(Map<String, Object> templateModel, String template) throws IOException, TemplateException {
        StringWriter sw = new StringWriter();
        freeMarker
                .getTemplate(template)
                .process(templateModel, sw);
        return sw.getBuffer().toString();

    }

    // send a simple email
    private SimpleMailMessage getSimpleMailMessage(Email theEmail) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(theEmail.getTo());
        simpleMailMessage.setFrom(theEmail.getFrom());
        simpleMailMessage.setSubject(theEmail.getSubject());
        simpleMailMessage.setText(theEmail.getText());
        return simpleMailMessage;
    }

    // send a multipart email
    private MimeMessageHelper getMimeMessageHelper(Email theEmail, MimeMessage mimeMessage) throws MessagingException {
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setTo(theEmail.getTo());
        mimeMessageHelper.setFrom(theEmail.getFrom());
        mimeMessageHelper.setSubject(theEmail.getSubject());
        mimeMessageHelper.setText(theEmail.getText());
        return mimeMessageHelper;
    }

    public Map<String, Object> buildTemplateModel(User user, String messageBody, String url, Long expiration) {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("firstName", user.getFirstName());
        templateModel.put("lastName", user.getLastName());
        templateModel.put("url", url);
        templateModel.put("expiration", expiration);
        templateModel.put("temporal", "minutes");
        templateModel.put("messageBody", messageBody);
        return templateModel;
    }
}
