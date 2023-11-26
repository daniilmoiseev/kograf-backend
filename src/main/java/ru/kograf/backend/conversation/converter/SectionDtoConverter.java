package ru.kograf.backend.conversation.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.kograf.backend.conversation.service.IKografConversionService;
import ru.kograf.backend.dto.SectionDto;
import ru.kograf.backend.model.Section;

@Component
public class SectionDtoConverter implements Converter<SectionDto, Section> {

    private final IKografConversionService conversionService;

    public SectionDtoConverter(IKografConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public Section convert(SectionDto source) {
        Section target = new Section();
        if (source.getId() != null) {
            target.setId(source.getId());
        }
        target.setTitle(source.getTitle());
        /*target.setUsers(conversionService.convert(source.getUsers(), User.class));
        target.setConference(conversionService.convert(source.getConference(), Conference.class));*/
        return target;
    }
}
