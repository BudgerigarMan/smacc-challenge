package com.smacc.challenge.failover.mailer.model;

import java.io.Serializable;

public class MailRequest implements Serializable {

    private String sender;
    private String reciever;
    private String subject;
    private String content;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MailRequest{" +
                "sender='" + sender + '\'' +
                ", reciever='" + reciever + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

