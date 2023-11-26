package ru.kograf.backend.conversation.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.kograf.backend.conversation.service.IKografConversionService;
import ru.kograf.backend.dto.CommentDto;
import ru.kograf.backend.model.Comment;

@Component
public class CommentDtoConverter implements Converter<CommentDto, Comment> {

    private final IKografConversionService conversionService;

    public CommentDtoConverter(IKografConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public Comment convert(CommentDto source) {
        Comment target = new Comment();
        if (source.getId() != null) {
            target.setId(source.getId());
        }
        /*target.setJob(conversionService.convert(source.getJob(), Job.class));
        target.setUser(conversionService.convert(source.getUser(), User.class));*/
        target.setMessage(source.getMessage());
        target.setDateTime(source.getDateTime());
        return target;
    }
}
