package ru.kograf.backend.conversation.converter;

import java.util.Collections;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.kograf.backend.conversation.service.IKografConversionService;
import ru.kograf.backend.dto.UserDto;
import ru.kograf.backend.model.Conference;
import ru.kograf.backend.model.Job;
import ru.kograf.backend.model.Section;
import ru.kograf.backend.model.User;

@Component
public class UserConverter implements Converter<User, UserDto> {

    private final IKografConversionService conversionService;

    public UserConverter(IKografConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public UserDto convert(User source) {
        UserDto target = new UserDto();
        if (source.getId() != null) {
            target.setId(source.getId());
        }
        target.setFullName(source.getFullName());
        target.setPhone(source.getPhone());
        target.setEmail(source.getEmail());
        target.setOrganization(source.getOrganization());
        target.setAcademicDegree(source.getAcademicDegree());
        target.setAcademicTitle(source.getAcademicTitle());
        target.setOrcId(source.getOrcId());
        target.setRincId(source.getRincId());
        target.setConferenceIds(source.getConferences() != null
                ? source.getConferences().stream().map(Conference::getId).toList()
                : Collections.emptyList());
        target.setSectionIds(source.getSections() != null
                ? source.getSections().stream().map(Section::getId).toList()
                : Collections.emptyList());
        target.setJobIds(source.getJobs() != null
                ? source.getJobs().stream().map(Job::getId).toList()
                : Collections.emptyList());
        target.setProfilePicture(source.getProfilePicture());
        target.setRole(source.getRole());
        target.setStatus(source.getStatus());
        return target;
    }
}
