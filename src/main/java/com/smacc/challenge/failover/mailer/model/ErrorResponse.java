package com.smacc.challenge.failover.mailer.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ErrorResponse {

    private int status;
    private String message;
}