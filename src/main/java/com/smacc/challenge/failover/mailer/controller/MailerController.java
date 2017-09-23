package com.smacc.challenge.failover.mailer.controller;

import org.apache.camel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.smacc.challenge.failover.mailer.model.MailRequest;

@RestController
@RequestMapping("/mailer")
public class MailerController {

    public static final String ACTIVEMQ_EMAIL_QUEUE = "activemq:email-queue";

    @Autowired
    private CamelContext camelContext;

    @Autowired
    private ProducerTemplate producerTemplate;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public void sendEmail(@RequestBody MailRequest mailContent) throws Exception {

        Endpoint endpoint = camelContext.getEndpoint(ACTIVEMQ_EMAIL_QUEUE);
        Exchange exchange = endpoint.createExchange();
        Message message = exchange.getIn();
        message.getHeaders().put("From", mailContent.getSender());
        message.getHeaders().put("To", mailContent.getReciever());
        message.getHeaders().put("Subject", mailContent.getSubject());
        message.setBody(mailContent.getContent());

        producerTemplate.send(ACTIVEMQ_EMAIL_QUEUE, exchange);
    }
}

