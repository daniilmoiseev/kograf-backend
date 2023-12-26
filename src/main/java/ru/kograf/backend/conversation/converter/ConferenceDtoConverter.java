package ru.kograf.backend.conversation.converter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime sd;
        try {
            sd = ZonedDateTime.parse(source.getStartDate(), dtf.withZone(ZoneId.of("Europe/Moscow")));
        } catch (DateTimeParseException ex) {
            sd = ZonedDateTime.parse(source.getStartDate()).withZoneSameInstant(ZoneId.of("Europe/Moscow"));
        } catch (NullPointerException ex) {
            sd = null;
        }
        ZonedDateTime ed;
        try {
            ed = ZonedDateTime.parse(source.getEndDate(), dtf.withZone(ZoneId.of("Europe/Moscow")));
        } catch (DateTimeParseException ex) {
            ed = ZonedDateTime.parse(source.getEndDate()).withZoneSameInstant(ZoneId.of("Europe/Moscow"));
        } catch (NullPointerException ex) {
            ed = null;
        }
        target.setStartDate(sd);
        target.setEndDate(ed);
        return target;
    }
}
