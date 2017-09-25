package com.smacc.challenge.failover.mailer.service;

import com.microtripit.mandrillapp.lutung.MandrillApi;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage;
import com.microtripit.mandrillapp.lutung.view.MandrillMessageStatus;
import com.sendgrid.*;
import com.smacc.challenge.failover.mailer.model.MailRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class MailerService {

    @Value("${main.mail.api.key}")
    String mainMailApiKey;

    @Value("${failover.mail.api.key}")
    String failoverMailApiKey;

    @Retryable(maxAttempts = 1, value = {Exception.class})
    public void sendEmail(MailRequest mailRequest) throws Exception {
        MandrillApi mandrillApi = new MandrillApi(failoverMailApiKey);
        MandrillMessage message = new MandrillMessage();
        message.setSubject(mailRequest.getSubject());
        message.setHtml(mailRequest.getContent());
        message.setAutoText(true);
        message.setFromEmail(mailRequest.getSender());
        ArrayList<MandrillMessage.Recipient> recipients = new ArrayList<>();
        MandrillMessage.Recipient recipient = new MandrillMessage.Recipient();
        recipient.setEmail(mailRequest.getReceiver());
        recipients.add(recipient);
        message.setTo(recipients);
        message.setPreserveRecipients(true);
        MandrillMessageStatus[] messageStatusReports = mandrillApi.messages().send(message, false);
        Map<String, String> responseStatuses = new HashMap<>();
        Arrays.stream(messageStatusReports).forEach(
                mandrillMessageStatus -> {
                    log.info("Response from Main mail server -> Status: {}, Reject reason: {}", mandrillMessageStatus.getStatus(), mandrillMessageStatus.getRejectReason().toString());
                    responseStatuses.put(mandrillMessageStatus.getStatus(), mandrillMessageStatus.getRejectReason());
                }
        );
        if (responseStatuses.containsKey("rejected") || responseStatuses.containsKey("invalid")) {
            throw new RuntimeException(responseStatuses.toString());
        }
    }

    @Recover
    private void sendMailFailover(MailRequest mailRequest) throws Exception {
        Email from = new Email(mailRequest.getSender());
        String subject = mailRequest.getSubject();
        Email to = new Email(mailRequest.getReceiver());
        Content content = new Content("text/plain", mailRequest.getContent());
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sendGrid = new SendGrid(mainMailApiKey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        Response response = sendGrid.api(request);
        log.info("Response from Failover mail server -> Status code: {}, Headers: {}, Body: {}", response.getStatusCode(), response.getHeaders().toString(), response.getBody());
    }
}
