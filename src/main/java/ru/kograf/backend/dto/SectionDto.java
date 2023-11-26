package ru.kograf.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionDto {

    private Long id;
    private Long leaderId;
    private String leaderName;
    private Long conferenceId;
    private String title;
}
