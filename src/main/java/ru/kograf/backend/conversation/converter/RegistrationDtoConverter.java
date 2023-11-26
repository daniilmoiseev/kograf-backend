package ru.kograf.backend.conversation.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.kograf.backend.dto.RegistrationDto;
import ru.kograf.backend.model.User;

@Component
public class RegistrationDtoConverter implements Converter<RegistrationDto, User> {

    @Override
    public User convert(RegistrationDto source) {
        User target = new User();
        target.setFullName(source.getFullName());
        target.setPhone(source.getPhone());
        target.setEmail(source.getEmail());
        target.setOrganization(source.getOrganization());
        target.setAcademicDegree(source.getAcademicDegree());
        target.setAcademicTitle(source.getAcademicTitle());
        return target;
    }
}
