package com.smacc.challenge.failover.mailer.controller;

import com.smacc.challenge.failover.mailer.model.ErrorResponse;
import com.smacc.challenge.failover.mailer.model.MailRequest;
import com.smacc.challenge.failover.mailer.service.MailerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.UndeclaredThrowableException;


@RestController
@RequestMapping("/mailer")
public class MailerController {

    @Autowired
    MailerService mailerService;

    @PostMapping("/send")
    public void sendEmail(@RequestBody MailRequest mailRequest) throws Exception {
        mailerService.sendEmail(mailRequest);
    }

    @ExceptionHandler(value = {Exception.class})
    private ResponseEntity<ErrorResponse> handleException(Exception exception) throws Throwable {
        Throwable undeclaredThrowable = exception;
        if (exception instanceof UndeclaredThrowableException) {
            undeclaredThrowable = ((UndeclaredThrowableException) exception).getUndeclaredThrowable();
        }
        ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(undeclaredThrowable.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
