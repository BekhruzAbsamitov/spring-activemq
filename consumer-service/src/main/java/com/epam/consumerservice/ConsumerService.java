package com.epam.consumerservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumerService {

    private final MessageProducerService messageProducerService;

    @JmsListener(destination = "${spring.jms.topic}")
    public void receive(Message message) throws JMSException {
        String s = messageProducerService.processOrder(message);
        log.info("Message received: {}", s);
    }

    @JmsListener(destination = "client.messages")
    public void receiveRequest(Message jmsMessage) throws JMSException {
        String s = messageProducerService.processRequest(jmsMessage);
        log.info("Message received: {}", s);
    }

}
