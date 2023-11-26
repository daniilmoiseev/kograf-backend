package ru.kograf.backend.service;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kograf.backend.conversation.service.IKografConversionService;
import ru.kograf.backend.dto.ConferenceDto;
import ru.kograf.backend.model.Conference;
import ru.kograf.backend.model.enums.ConferenceStatus;
import ru.kograf.backend.repository.ConferenceRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConferenceService {

    private final IKografConversionService conversionService;
    private final ConferenceRepository conferenceRepository;

    public List<ConferenceDto> getConferencesPublic() {
        List<Conference> conferencesFromDb = conferenceRepository.findAll();
        return conversionService.convert(conferencesFromDb, ConferenceDto.class)
                .stream()
                .peek(e -> {
                    e.setCountUsers(e.getUserIds().size());
                    e.setUserIds(Collections.emptyList());
                })
                .toList();
    }

    public List<ConferenceDto> getConferencesAdmin() {
        List<Conference> conferencesFromDb = conferenceRepository.findAll();
        return conversionService.convert(conferencesFromDb, ConferenceDto.class)
                .stream()
                .peek(e -> {
                    e.setCountUsers(e.getUserIds().size());
                })
                .toList();
    }

    public ConferenceDto createConference(ConferenceDto conferenceDto) {
        Conference conference = conversionService.convert(conferenceDto, Conference.class);
        if (conference != null) {
            ConferenceStatus status = conference.getStatus();
            conference.setStatus(status == null
                    ? ConferenceStatus.ON_HOLD
                    : status);
            ZonedDateTime startDate = conference.getStartDate();
            ZonedDateTime endDate = conference.getEndDate();
            conference.setStartDate(startDate == null
                    ? ZonedDateTime.now()
                    : startDate);
            conference.setEndDate(endDate == null
                    ? ZonedDateTime.now().plusDays(14)
                    : endDate);
            conference.setSections(Collections.emptyList());

            Conference saved = conferenceRepository.save(conference);

            return conversionService.convert(saved, ConferenceDto.class);
        } else {
            log.warn("Unable to convert conference {}", conferenceDto.getTitle());
        }

        return null;
    }
}
