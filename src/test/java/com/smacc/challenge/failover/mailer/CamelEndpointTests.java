package com.smacc.challenge.failover.mailer;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.DisableJmx;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.Assert.assertNotNull;

@ActiveProfiles("test")
@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisableJmx
public class CamelEndpointTests {

    @Autowired
    private CamelContext camelContext;

    @EndpointInject(uri = "activemq:email-queue")
    private ProducerTemplate mqProducerTemplate;

    @Test
    @DirtiesContext
    public void testCamelActiveMQEndPointIsFunctioning() throws Exception {
        assertNotNull(camelContext);
        assertNotNull(mqProducerTemplate);
        mqProducerTemplate.sendBody("Test Message");
        MockEndpoint.assertIsSatisfied(camelContext);
    }
}
