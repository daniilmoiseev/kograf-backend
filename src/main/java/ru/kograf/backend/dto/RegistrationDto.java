package ru.kograf.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {

    private String fullName;
    private String phone;
    private String email;
    private String organization;
    private String academicDegree;
    private String academicTitle;
    private String password;
}
