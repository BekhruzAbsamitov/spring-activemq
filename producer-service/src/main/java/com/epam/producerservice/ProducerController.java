package com.epam.producerservice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProducerController {

    private final ProducerService producerService;

    @PostMapping
    public void sendMessage(@RequestParam String message) {
        producerService.sendMessageToTopic(message);
    }

    @PostMapping("/temp-queue")
    public void sendMessageToTempQueue(@RequestParam String message) {
        producerService.sendMessageToTempQueue(message);
    }

}
