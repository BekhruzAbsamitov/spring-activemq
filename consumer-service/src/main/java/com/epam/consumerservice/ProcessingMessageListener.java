package com.epam.consumerservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Component
@Slf4j
public class ProcessingMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            String text = ((TextMessage) message).getText();
            log.info("Caught message: {}", text);
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
    }
}