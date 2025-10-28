package com.muhammadminhaz.analyticsservice.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

import java.util.logging.Logger;

@Service
public class KafkaConsumer {

    private static final Logger logger = Logger.getLogger(KafkaConsumer.class.getName());


    @KafkaListener(topics = "patient", groupId = "analytics-service")
    public void consumePatientCreateEvent(byte[] event) {
        logger.info("Received Patient Event from Kafka Consumer");

        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);
            logger.info("Successfully parsed PatientEvent");
        } catch (InvalidProtocolBufferException e) {
            logger.info("Failed to parse PatientEvent");
        }
    }

}