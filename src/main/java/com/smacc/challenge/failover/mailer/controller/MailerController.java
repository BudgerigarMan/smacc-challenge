package com.smacc.challenge.failover.mailer.controller;

import com.smacc.challenge.failover.mailer.model.ErrorResponse;
import com.smacc.challenge.failover.mailer.model.MailRequest;
import com.smacc.challenge.failover.mailer.service.MailerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.UndeclaredThrowableException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;


@RestController
@RequestMapping("/mailer")
@Slf4j
public class MailerController {

    @Autowired
    MailerService mailerService;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public void sendEmail(@RequestBody MailRequest mailRequest) throws Exception {
        mailerService.sendEmail(mailRequest);
    }

    @ExceptionHandler(value = {UndeclaredThrowableException.class})
    private ResponseEntity<ErrorResponse> handleException(UndeclaredThrowableException exception) throws Throwable {
        Throwable undeclaredThrowable = exception.getUndeclaredThrowable();
        ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(undeclaredThrowable.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
