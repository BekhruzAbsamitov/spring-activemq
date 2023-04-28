package com.epam.producerservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.TextMessage;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
@EnableJms
public class ProducerService {

    private final JmsTemplate jmsTemplate;

    @Value("${spring.jms.topic}")
    private String topic;

    @Value("${spring.jms.queue}")
    private String queue;

    @Transactional
    public void sendMessageToTopic(String message) {
        jmsTemplate.convertAndSend(topic, message);
        log.info("Sent message: {}", message);
    }

    @Transactional
    public void sendMessageToTempQueue(String message) {
        jmsTemplate.send(queue, session -> {
            TextMessage textMessage = session.createTextMessage(message);
            textMessage.setJMSReplyTo(session.createTemporaryQueue());
            String randimString = createRandimString();
            textMessage.setJMSCorrelationID(randimString);
            log.info(randimString);
            return textMessage;
        });
        log.info("Message '{}' sent to temporary queue", message);
    }

    private String createRandimString() {
        Random random = new Random(System.currentTimeMillis());
        long nextLong = random.nextLong();
        return Long.toHexString(nextLong);
    }

}
