package ru.kograf.backend.conversation.converter;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        target.setStartDate(dtf.format(source.getStartDate().withZoneSameInstant(ZoneId.of("Europe/Moscow"))));
        target.setEndDate(dtf.format(source.getEndDate().withZoneSameInstant(ZoneId.of("Europe/Moscow"))));
        return target;
    }
}
