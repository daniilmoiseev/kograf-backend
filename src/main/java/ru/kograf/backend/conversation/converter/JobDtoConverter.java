package ru.kograf.backend.conversation.converter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.kograf.backend.conversation.service.IKografConversionService;
import ru.kograf.backend.dto.JobDto;
import ru.kograf.backend.model.Comment;
import ru.kograf.backend.model.Conference;
import ru.kograf.backend.model.Job;
import ru.kograf.backend.model.Section;
import ru.kograf.backend.model.User;
import ru.kograf.backend.repository.ConferenceRepository;
import ru.kograf.backend.repository.SectionRepository;
import ru.kograf.backend.repository.UserRepository;

@Component
public class JobDtoConverter implements Converter<JobDto, Job> {

    private final IKografConversionService conversionService;
    private final UserRepository userRepository;
    private final ConferenceRepository conferenceRepository;
    private final SectionRepository sectionRepository;

    public JobDtoConverter(IKografConversionService conversionService, UserRepository userRepository,
            ConferenceRepository conferenceRepository, SectionRepository sectionRepository) {
        this.conversionService = conversionService;
        this.userRepository = userRepository;
        this.conferenceRepository = conferenceRepository;
        this.sectionRepository = sectionRepository;
    }

    @SneakyThrows
    @Override
    public Job convert(JobDto source) {
        Job target = new Job();
        if (source.getId() != null) {
            target.setId(source.getId());
        }
        target.setTitle(source.getTitle());
        target.setDescription(source.getDescription());
        target.setCoAuthors(source.getCoAuthors());
        if (source.getUserId() != null) {
            User user = userRepository.findById(source.getUserId())
                    .orElseThrow(() -> new Exception("Unable to find user by id " + source.getUserId()));
            target.setUser(user);
        }
        if (source.getConferenceId() != null) {
            Conference conference = conferenceRepository.findById(source.getConferenceId())
                    .orElseThrow(() -> new Exception("Unable to find conference by id " + source.getConferenceId()));
            target.setConference(conference);
        }
        if (source.getSectionId() != null) {
            Section section = sectionRepository.findById(source.getSectionId())
                    .orElseThrow(() -> new Exception("Unable to find section by id " + source.getSectionId()));
            target.setSection(section);
        }
        target.setComments(conversionService.convert(source.getComments(), Comment.class));

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime dt;
        try {
            dt = ZonedDateTime.parse(source.getDateTime(), dtf.withZone(ZoneId.of("Europe/Moscow")));
        } catch (DateTimeParseException ex) {
            dt = ZonedDateTime.parse(source.getDateTime()).withZoneSameInstant(ZoneId.of("Europe/Moscow"));
        } catch (Exception ex) {
            dt = null;
        }
        target.setDateTime(dt);
        target.setFileName(source.getFileName());
        return target;
    }
}
