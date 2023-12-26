package ru.kograf.backend.dto;

import java.time.ZonedDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kograf.backend.model.enums.ConferenceStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConferenceDto {

    private Long id;
    private String title;
    private String organization;
    private String description;
    private List<SectionDto> sections;
    private ConferenceStatus status;
    private List<Long> userIds;
    private Integer countUsers;
    private String startDate;
    private String endDate;
}
