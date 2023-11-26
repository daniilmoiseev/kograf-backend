package ru.kograf.backend.conversation.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.kograf.backend.conversation.service.IKografConversionService;
import ru.kograf.backend.dto.CommentDto;
import ru.kograf.backend.model.Comment;

@Component
public class CommentConverter implements Converter<Comment, CommentDto> {

    private final IKografConversionService conversionService;

    public CommentConverter(IKografConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public CommentDto convert(Comment source) {
        CommentDto target = new CommentDto();
        if (source.getId() != null) {
            target.setId(source.getId());
        }
        target.setJobId(source.getJob().getId());
        target.setUserId(source.getUser().getId());
        target.setMessage(source.getMessage());
        target.setDateTime(source.getDateTime());
        return target;
    }
}
