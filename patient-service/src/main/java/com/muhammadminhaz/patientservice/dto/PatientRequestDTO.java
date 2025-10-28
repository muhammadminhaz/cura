package com.muhammadminhaz.patientservice.dto;

import com.muhammadminhaz.patientservice.dto.validators.CreatePatientValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PatientRequestDTO {
    @NotBlank
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "Please provide an email")
    @Email(message = "Please provide a valid email")
    private String email;

    @NotBlank(message = "Please provide an address")
    private String address;

    @NotBlank(message = "Please provide a gender")
    private String gender;

    @NotBlank(message = "Please provide a date of birth")
    private String birthDate;

    @NotBlank(groups = CreatePatientValidationGroup.class, message = "Please provide a registration date")
    private String registrationDate;
}
