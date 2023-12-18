package ru.kograf.backend.conversation.converter;

import java.util.Collections;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.kograf.backend.conversation.service.IKografConversionService;
import ru.kograf.backend.dto.ConferenceDto;
import ru.kograf.backend.dto.SectionDto;
import ru.kograf.backend.model.Conference;
import ru.kograf.backend.model.User;

@Component
public class ConferenceConverter implements Converter<Conference, ConferenceDto> {

    private final IKografConversionService conversionService;

    public ConferenceConverter(IKografConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public ConferenceDto convert(Conference source) {
        ConferenceDto target = new ConferenceDto();
        if (source.getId() != null) {
            target.setId(source.getId());
        }
        target.setTitle(source.getTitle());
        target.setOrganization(source.getOrganization());
        target.setDescription(source.getDescription());
        target.setSections(conversionService.convert(source.getSections(), SectionDto.class));
        target.setStatus(source.getStatus());
        target.setUserIds(source.getUsers() != null
                ? source.getUsers().stream().map(User::getId).toList()
                : Collections.emptyList());
        target.setStartDate(source.getStartDate());
        target.setEndDate(source.getEndDate());
        return target;
    }
}
