package ru.kograf.backend.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kograf.backend.model.enums.Role;
import ru.kograf.backend.model.enums.UserStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String fullName;
    private String phone;
    private String email;
    private String organization;
    private String academicDegree;
    private String academicTitle;
    private String orcId;
    private String rincId;
    private List<Long> conferenceIds;
    private List<Long> sectionIds;
    private List<Long> jobIds;
    private String profilePicture;
    private Role role;
    private UserStatus status;
}
