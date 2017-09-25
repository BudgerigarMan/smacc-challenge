package com.smacc.challenge.failover.mailer.controller;

import com.smacc.challenge.failover.mailer.model.MailRequest;
import com.smacc.challenge.failover.mailer.service.MailerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MailerController.class)
public class MailerControllerTest {

    public static final String MAILER_SEND_URL = "/mailer/send";
    @MockBean
    MailerService mailerService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenPostMethodAndServiceRaisesException_whenSendEmail_thenReturnBadRequestResponseCode() throws Exception {
        RuntimeException toBeThrown = new RuntimeException("Mail server returned error");
        doThrow(toBeThrown).when(mailerService).sendEmail(any(MailRequest.class));
        mockMvc.perform(post(MAILER_SEND_URL).contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenPostMethodAndServiceDoesNotRaiseException_whenSendEmail_thenReturnOkResponseCode() throws Exception {
        doNothing().when(mailerService).sendEmail(any(MailRequest.class));
        mockMvc.perform(post(MAILER_SEND_URL).contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenGetMethod_whenSendEmail_thenReturnOkResponseCode() throws Exception {
        mockMvc.perform(get(MAILER_SEND_URL).contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isMethodNotAllowed());
    }
}
