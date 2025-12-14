package com.muhammadminhaz.patientservice.kafka;

import billing.events.BillingAccountEvent;
import com.muhammadminhaz.patientservice.model.Patient;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

import java.util.logging.Logger;

@Service
public class KafkaProducer {
    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private static final Logger logger = Logger.getLogger(KafkaProducer.class.getName());

    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPatientCreateEvent(Patient patient) {
        PatientEvent patientEvent = PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setEmail(patient.getEmail())
                .setName(patient.getName())
                .build();
        try {
            logger.info(">>> Sending patient event to Kafka: {}" + patientEvent);
            kafkaTemplate.send("patient.created", patientEvent.toByteArray());
        } catch (Exception e) {
            logger.info("Error sending Patient Create Event: {}" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void sendPatientUpdateEvent(Patient patient) {
        PatientEvent patientEvent = PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setEmail(patient.getEmail())
                .setName(patient.getName())
                .build();
        try {
            logger.info(">>> Sending patient event to Kafka: {}" + patientEvent);
            kafkaTemplate.send("patient.updated", patientEvent.toByteArray());
        } catch (Exception e) {
            logger.info("Error sending Patient Update Event: {}" + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public void sendBillingAccountEvent(String patientId, String name, String email) {
        BillingAccountEvent event = BillingAccountEvent.newBuilder()
                .setPatientId(patientId)
                .setName(name)
                .setEmail(email)
                .setEventType("BILLING_ACCOUNT_CREATE_REQUESTED")
                .build();

        try {
            logger.info(">>> Sending billing account event to Kafka: {}" + event);
            kafkaTemplate.send("billing-account", event.toByteArray());
        } catch (Exception e) {
            logger.info("Error sending BILLING_ACCOUNT_CREATE_REQUESTED: {}" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
