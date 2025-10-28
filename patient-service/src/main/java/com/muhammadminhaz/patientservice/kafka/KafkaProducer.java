package com.muhammadminhaz.patientservice.kafka;

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
                .setEventType("PATIENT_EVENT_CREATED")
                .build();
        try {
            logger.info(">>> Sending patient event to Kafka: {}" + patientEvent);
            kafkaTemplate.send("patient", patientEvent.toByteArray());
        } catch (Exception e) {
            logger.info("Error sending PATIENT_EVENT_CREATED: {}" + e.getMessage());
            throw new RuntimeException(e);
        }
    }


}
