package com.epam.consumerservice;

import lombok.AllArgsConstructor;

import java.util.Date;

@AllArgsConstructor
public class ProcessedProducerMessage {

    private String message;
    private Date processongDate;
    private Date expectedShippingDate;



}
