package ru.kograf.backend.dto;

import java.time.ZonedDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDto {

    private Long id;
    private String title;
    private String description;
    private String coAuthors;
    private Long userId;
    private Long conferenceId;
    private Long sectionId;
    private List<CommentDto> comments;
    private ZonedDateTime dateTime;
    private String sourceFile;
}
