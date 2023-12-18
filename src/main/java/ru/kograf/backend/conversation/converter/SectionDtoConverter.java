package ru.kograf.backend.conversation.converter;

import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.kograf.backend.conversation.service.IKografConversionService;
import ru.kograf.backend.dto.SectionDto;
import ru.kograf.backend.model.Conference;
import ru.kograf.backend.model.Section;
import ru.kograf.backend.repository.ConferenceRepository;

@Component
public class SectionDtoConverter implements Converter<SectionDto, Section> {

    private final IKografConversionService conversionService;
    private final ConferenceRepository conferenceRepository;

    public SectionDtoConverter(IKografConversionService conversionService, ConferenceRepository conferenceRepository) {
        this.conversionService = conversionService;
        this.conferenceRepository = conferenceRepository;
    }

    @SneakyThrows
    @Override
    public Section convert(SectionDto source) {
        Section target = new Section();
        if (source.getId() != null) {
            target.setId(source.getId());
        }
        target.setTitle(source.getTitle());
        target.setLeaderName(source.getLeaderName());
        if (source.getConferenceId() != null) {
            Conference conference = conferenceRepository.findById(source.getConferenceId())
                    .orElseThrow(() -> new Exception("Unable to find conference by id " + source.getConferenceId()));
            target.setConference(conference);
        }
        return target;
    }
}
