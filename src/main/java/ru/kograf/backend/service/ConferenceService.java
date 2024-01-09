package ru.kograf.backend.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kograf.backend.conversation.service.IKografConversionService;
import ru.kograf.backend.dto.ConferenceDto;
import ru.kograf.backend.dto.SectionDto;
import ru.kograf.backend.model.Conference;
import ru.kograf.backend.model.Section;
import ru.kograf.backend.model.User;
import ru.kograf.backend.model.enums.ConferenceStatus;
import ru.kograf.backend.repository.ConferenceRepository;
import ru.kograf.backend.repository.SectionRepository;
import ru.kograf.backend.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConferenceService {

    private final IKografConversionService conversionService;
    private final ConferenceRepository conferenceRepository;
    private final SectionRepository sectionRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ConferenceDto> getConferencesPublic() {
        List<Conference> conferencesFromDb = conferenceRepository.findAll();
        return conversionService.convert(conferencesFromDb, ConferenceDto.class)
                .stream()
                .peek(e -> {
                    e.setUserIds(Collections.emptyList());
                })
                .sorted(Comparator.comparing(ConferenceDto::getTitle))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ConferenceDto> getConferencesAdmin() {
        List<Conference> conferencesFromDb = conferenceRepository.findAll();
        return conversionService.convert(conferencesFromDb, ConferenceDto.class)
                .stream()
                .peek(e -> {
                    e.setCountUsers(e.getUserIds().size());
                })
                .sorted(Comparator.comparing(ConferenceDto::getTitle))
                .toList();
    }

    @SneakyThrows
    @Transactional(readOnly = true)
    public ConferenceDto getConferenceAdmin(Long id) {
        Conference conferencesFromDb = conferenceRepository.findById(id)
                .orElseThrow(() -> new Exception("Unable to find conference by id " + id));

        ConferenceDto convert = conversionService.convert(conferencesFromDb, ConferenceDto.class);
        convert.setCountUsers(convert.getUserIds().size());
        return convert;
    }

    @SneakyThrows
    @Transactional(readOnly = true)
    public ConferenceDto getConferencePublic(Long id) {
        Conference conferencesFromDb = conferenceRepository.findById(id)
                .orElseThrow(() -> new Exception("Unable to find conference by id " + id));

        ConferenceDto convert = conversionService.convert(conferencesFromDb, ConferenceDto.class);
        convert.setUserIds(Collections.emptyList());
        return convert;
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
                    ? ZonedDateTime.now(ZoneId.of("Europe/Moscow"))
                    : startDate);
            conference.setEndDate(endDate == null
                    ? ZonedDateTime.now(ZoneId.of("Europe/Moscow")).plusDays(14)
                    : endDate);

            List<Section> sections = conference.getSections();
            conference.setSections(Collections.emptyList());
            Conference saved = conferenceRepository.save(conference);

            if (CollectionUtils.isNotEmpty(sections)) {
                sections.forEach(section -> section.setConference(saved));
                saveSections(sections);
                saved.setSections(sections);
            }
            return conversionService.convert(saved, ConferenceDto.class);
        } else {
            log.warn("Unable to convert conference {}", conferenceDto.getTitle());
        }

        return null;
    }

    @SneakyThrows
    public ConferenceDto updateConference(Long id, ConferenceDto conferenceDto) {
        Conference conferenceFromDb = conferenceRepository.findById(id)
                .orElseThrow(() -> new Exception("Unable to find conference by id " + id));

        Conference conference = conversionService.convert(conferenceDto, Conference.class);
        if (conferenceFromDb != null) {
            conferenceFromDb.setTitle(conference.getTitle());
            conferenceFromDb.setOrganization(conference.getOrganization());
            conferenceFromDb.setDescription(conference.getDescription());

            ConferenceStatus status = conference.getStatus();
            conferenceFromDb.setStatus(status == null
                    ? ConferenceStatus.ON_HOLD
                    : status);
            ZonedDateTime startDate = conference.getStartDate();
            ZonedDateTime endDate = conference.getEndDate();
            conferenceFromDb.setStartDate(startDate == null
                    ? ZonedDateTime.now(ZoneId.of("Europe/Moscow"))
                    : startDate);
            conferenceFromDb.setEndDate(endDate == null
                    ? ZonedDateTime.now(ZoneId.of("Europe/Moscow")).plusDays(14)
                    : endDate);

            Conference saved = conferenceRepository.save(conferenceFromDb);
            List<Section> sections = conference.getSections();

            if (CollectionUtils.isEmpty(sections)) {
                saved.setSections(Collections.emptyList());
            } else {
                sections.forEach(e -> {
                    if (e.getConference() == null) {
                        e.setConference(saved);
                    }
                });
                saveSections(sections);
                saved.setSections(sections);
            }

            return conversionService.convert(saved, ConferenceDto.class);
        } else {
            log.warn("Unable to update conference {} {}", conferenceDto.getTitle(), conferenceDto.getId());
        }

        return null;
    }

    @Transactional(readOnly = true)
    public List<SectionDto> getSections(Long id) {
        List<Section> sectionsFromDb = sectionRepository.getSectionsByConferenceId(id);
        return conversionService.convert(sectionsFromDb, SectionDto.class);
    }

    public boolean saveSections(List<Section> sections) {
        List<Section> savedSections = sectionRepository.saveAll(sections);
        return CollectionUtils.isEmpty(savedSections);
    }

    @SneakyThrows
    public void appointAdmin(Long id, Long userId) {
        Conference conferenceFromDb = conferenceRepository.findById(id)
                .orElseThrow(() -> new Exception("Unable to find conference by id " + id));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("Unable to find user by id " + userId));

        conferenceFromDb.setAdmin(user);
        conferenceRepository.save(conferenceFromDb);
    }

    @SneakyThrows
    public void disappointAdmin(Long id) {
        Conference conferenceFromDb = conferenceRepository.findById(id)
                .orElseThrow(() -> new Exception("Unable to find conference by id " + id));

        conferenceFromDb.setAdmin(null);
        conferenceRepository.save(conferenceFromDb);
    }
}
