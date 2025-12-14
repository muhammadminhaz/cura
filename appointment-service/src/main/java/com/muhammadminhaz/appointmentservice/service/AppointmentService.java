package com.muhammadminhaz.appointmentservice.service;

import com.muhammadminhaz.appointmentservice.dto.AppointmentResponseDto;
import com.muhammadminhaz.appointmentservice.entity.CachedPatient;
import com.muhammadminhaz.appointmentservice.repository.AppointmentRepository;
import com.muhammadminhaz.appointmentservice.repository.CachedPatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final CachedPatientRepository cachedPatientRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, CachedPatientRepository cachedPatientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.cachedPatientRepository = cachedPatientRepository;
    }

    public List<AppointmentResponseDto> getAppointmentsByDateRange(LocalDateTime from, LocalDateTime to) {

        return appointmentRepository.findByStartTimeBetween(from, to)
                .stream()
                .map(appointment -> {
                    AppointmentResponseDto appointmentResponseDto = new AppointmentResponseDto();
                    appointmentResponseDto.setId(appointment.getId());
                    appointmentResponseDto.setPatientId(appointment.getPatientId());
                    appointmentResponseDto.setPatientName(
                            cachedPatientRepository
                                    .findById(appointment.getPatientId())
                                    .map(CachedPatient::getFullName)
                                    .orElse("Unknown"));
                    appointmentResponseDto.setStartTime(appointment.getStartTime());
                    appointmentResponseDto.setEndTime(appointment.getEndTime());
                    appointmentResponseDto.setReason(appointment.getReason());
                    appointmentResponseDto.setVersion(appointment.getVersion());
                    return appointmentResponseDto;
                }).toList();
    }
}
