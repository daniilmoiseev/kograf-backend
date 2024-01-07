package ru.kograf.backend.dto;

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
    private String userName;
    private String conferenceTitle;
    private String sectionTitle;
    private Long userId;
    private Long conferenceId;
    private Long sectionId;
    private List<CommentDto> comments;
    private String dateTime;
    private String fileName;
}
