package ru.kograf.backend.conversation.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.kograf.backend.conversation.service.IKografConversionService;
import ru.kograf.backend.dto.ConferenceDto;
import ru.kograf.backend.model.Conference;
import ru.kograf.backend.model.Section;

@Component
public class ConferenceDtoConverter implements Converter<ConferenceDto, Conference> {

    private final IKografConversionService conversionService;

    public ConferenceDtoConverter(IKografConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public Conference convert(ConferenceDto source) {
        Conference target = new Conference();
        if (source.getId() != null) {
            target.setId(source.getId());
        }
        target.setTitle(source.getTitle());
        target.setOrganization(source.getOrganization());
        target.setDescription(source.getDescription());
        target.setSections(conversionService.convert(source.getSections(), Section.class));
        target.setStatus(source.getStatus());
        //target.setUsers(conversionService.convert(source.getUsers(), User.class));
        target.setStartDate(source.getStartDate());
        target.setEndDate(source.getEndDate());
        return target;
    }
}
