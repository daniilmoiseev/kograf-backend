package ru.kograf.backend.conversation.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.kograf.backend.conversation.service.IKografConversionService;
import ru.kograf.backend.dto.UserDto;
import ru.kograf.backend.model.User;

@Component
public class UserDtoConverter implements Converter<UserDto, User> {

    private final IKografConversionService conversionService;

    public UserDtoConverter(IKografConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public User convert(UserDto source) {
        User target = new User();
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
        /*target.setConferences(conversionService.convert(source.getConferences(), Conference.class));
        target.setSections(conversionService.convert(source.getSections(), Section.class));
        target.setJobs(conversionService.convert(source.getJobs(), Job.class));*/
        target.setProfilePicture(source.getProfilePicture());
        target.setRole(source.getRole());
        target.setStatus(source.getStatus());

        return target;
    }
}
