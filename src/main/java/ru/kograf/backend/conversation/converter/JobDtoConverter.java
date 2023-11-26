package ru.kograf.backend.conversation.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.kograf.backend.conversation.service.IKografConversionService;
import ru.kograf.backend.dto.JobDto;
import ru.kograf.backend.model.Comment;
import ru.kograf.backend.model.Job;

@Component
public class JobDtoConverter implements Converter<JobDto, Job> {

    private final IKografConversionService conversionService;

    public JobDtoConverter(IKografConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public Job convert(JobDto source) {
        Job target = new Job();
        if (source.getId() != null) {
            target.setId(source.getId());
        }
        target.setTitle(source.getTitle());
        target.setDescription(source.getDescription());
        target.setCoAuthors(source.getCoAuthors());
        /*target.setUser(conversionService.convert(source.getUser(), User.class));
        target.setConference(conversionService.convert(source.getConference(), Conference.class));
        target.setSection(conversionService.convert(source.getSection(), Section.class));*/
        target.setComments(conversionService.convert(source.getComments(), Comment.class));
        target.setDateTime(source.getDateTime());
        target.setSourceFile(source.getSourceFile());
        return target;
    }
}
