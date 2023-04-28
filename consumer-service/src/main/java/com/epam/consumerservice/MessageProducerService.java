package com.epam.consumerservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageProducerService {

    private final JmsTemplate jmsTemplate;

    @Transactional
    public String processOrder(Message message) throws JMSException {
        return Optional.ofNullable(((TextMessage) message).getText()).orElse("Content is null");
    }

    //@Transactional
    public String processRequest(Message jmsMessage) throws JMSException {
        String response = "Response to: " + (TextMessage) jmsMessage;
        log.info(jmsMessage.getJMSCorrelationID());
        jmsTemplate.send(jmsMessage.getJMSReplyTo(), session -> {
            TextMessage responseMessage = session.createTextMessage(response);
            responseMessage.setJMSCorrelationID(jmsMessage.getJMSCorrelationID());
            return responseMessage;
        });
        return response;
    }
}
