package com.restaurant.email;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Email  {

    private String to;
    private String from;
    private String subject;
    private String content;
    private String text;
    private Map<String, Object> templateModel;

    public static class builder {

        private final String to;
        private String from;
        private String subject;
        private String content;
        private String text;
        private Map<String, Object> templateModel;

        private builder(String to) {
            this.to = to;
            this.templateModel = new HashMap<>();
        }

        public static builder setTo(String to) {
            return new builder(to);
        }

        public builder setFrom(String from) {
            this.from = from;
            return this;
        }

        public builder withSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public builder withContent(String content) {
            this.content = content;
            return this;
        }

        public builder withText(String text) {
            this.text = text;
            return this;
        }

        public builder withModel(Map<String, Object> templateModel) {
            this.templateModel = templateModel;
            return this;
        }

        public Email build() {
            Email email = new Email();
            email.setTo(this.to);
            email.setFrom(this.from);
            email.setSubject(this.subject);
            email.setContent(this.content);
            email.setText(this.text);
            email.setTemplateModel(this.templateModel);
            return email;
        }


    }

}
