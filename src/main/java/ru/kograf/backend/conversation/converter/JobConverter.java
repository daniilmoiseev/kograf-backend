package ru.kograf.backend.conversation.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.kograf.backend.conversation.service.IKografConversionService;
import ru.kograf.backend.dto.CommentDto;
import ru.kograf.backend.dto.JobDto;
import ru.kograf.backend.model.Job;

@Component
public class JobConverter implements Converter<Job, JobDto> {

    private final IKografConversionService conversionService;

    public JobConverter(IKografConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public JobDto convert(Job source) {
        JobDto target = new JobDto();
        if (source.getId() != null) {
            target.setId(source.getId());
        }
        target.setTitle(source.getTitle());
        target.setDescription(source.getDescription());
        target.setCoAuthors(source.getCoAuthors());
        target.setUserId(source.getUser().getId());
        target.setConferenceId(source.getConference().getId());
        target.setSectionId(source.getSection().getId());
        target.setComments(conversionService.convert(source.getComments(), CommentDto.class));
        target.setDateTime(source.getDateTime());
        target.setSourceFile(source.getSourceFile());
        return target;
    }
}
