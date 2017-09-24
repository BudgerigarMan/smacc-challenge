package com.smacc.challenge.failover.mailer.controller;

import com.sendgrid.*;
import com.smacc.challenge.failover.mailer.model.MailRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/mailer")
@Slf4j
public class MailerController {

    @Value("${main.mail.api.key}")
    String mainApiKey;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public void sendEmail(@RequestBody MailRequest mailContent) throws Exception {

        Email from = new Email(mailContent.getSender());
        String subject = mailContent.getSubject();
        Email to = new Email(mailContent.getReceiver());
        Content content = new Content("text/plain", mailContent.getContent());
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(mainApiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            log.info(response.getStatusCode() + "");
            log.info(response.getBody());
            log.info(response.getHeaders().toString());
        } catch (IOException ex) {
            throw ex;
        }
    }
}

