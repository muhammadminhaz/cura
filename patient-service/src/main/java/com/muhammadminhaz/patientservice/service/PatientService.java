package com.muhammadminhaz.patientservice.service;

import com.muhammadminhaz.patientservice.dto.PaginatedPatientResponseDTO;
import com.muhammadminhaz.patientservice.dto.PatientRequestDTO;
import com.muhammadminhaz.patientservice.dto.PatientResponseDTO;
import com.muhammadminhaz.patientservice.exception.EmailAlreadyExistsException;
import com.muhammadminhaz.patientservice.exception.PatientNotFoundException;
import com.muhammadminhaz.patientservice.grpc.BillingServiceGrpcClient;
import com.muhammadminhaz.patientservice.kafka.KafkaProducer;
import com.muhammadminhaz.patientservice.mapper.PatientMapper;
import com.muhammadminhaz.patientservice.model.Patient;
import com.muhammadminhaz.patientservice.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Slf4j
@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;
    private static final Logger logger =  Logger.getLogger(PatientService.class.getName());

    public PatientService(PatientRepository patientRepository, BillingServiceGrpcClient billingServiceGrpcClient, KafkaProducer kafkaProducer) {
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
    }

    @Cacheable(
            value = "patients",
            key = "#page + '-' + #size + '-' + #sort + '-' + #sortBy",
            condition = "#searchValue == ''"
    )
    public PaginatedPatientResponseDTO getPatients(int page, int size, String sort, String sortBy, String searchValue) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
        Pageable pageable = PageRequest.of(page - 1, size, sort.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending());
        Page<Patient> patientPage;

        if (searchValue == null || searchValue.isBlank()) {
            patientPage = patientRepository.findAll(pageable);
        } else {
            patientPage = patientRepository.findByNameContainingIgnoreCase(searchValue, pageable);
        }

        List<PatientResponseDTO> patientResponseDtos = patientPage.getContent().stream().map(PatientMapper::toDTO).toList();

        return new PaginatedPatientResponseDTO(
                patientResponseDtos,
                patientPage.getNumber() + 1,
                patientPage.getSize(),
                patientPage.getTotalPages(),
                (int) patientPage.getTotalElements()
        );
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {

        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("A patient with the given email already exists [ " + patientRequestDTO.getEmail() + " ]");
        }
        Patient patient = patientRepository.save(PatientMapper.toEntity(patientRequestDTO));
        logger.info("Created patient [{patient.getName()}]" +  patient.getName());
        billingServiceGrpcClient.createBilling(String.valueOf(patient.getId()), patient.getName(), patient.getEmail());
        kafkaProducer.sendPatientCreateEvent(patient);
        return PatientMapper.toDTO(patient);
    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient Not found"));
        if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)) {
            throw new EmailAlreadyExistsException("A patient with the given email already exists [ " + patientRequestDTO.getEmail() + " ]");
        }
        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setGender(patientRequestDTO.getGender());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setBirthDate(LocalDate.parse(patientRequestDTO.getBirthDate()));
        return PatientMapper.toDTO(patientRepository.save(patient));
    }

    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }

}
