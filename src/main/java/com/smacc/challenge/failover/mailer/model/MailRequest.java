package com.smacc.challenge.failover.mailer.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class MailRequest implements Serializable {

    private String sender;
    private String receiver;
    private String subject;
    private String content;
}

