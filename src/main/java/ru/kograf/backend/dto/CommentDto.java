package ru.kograf.backend.dto;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;
    private Long jobId;
    private Long userId;
    private String message;
    private ZonedDateTime dateTime;
}
