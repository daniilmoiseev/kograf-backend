package ru.kograf.backend.conversation.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.kograf.backend.conversation.service.IKografConversionService;
import ru.kograf.backend.dto.SectionDto;
import ru.kograf.backend.model.Section;

@Component
public class SectionConverter implements Converter<Section, SectionDto> {

    private final IKografConversionService conversionService;

    public SectionConverter(IKografConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public SectionDto convert(Section source) {
        SectionDto target = new SectionDto();
        if (source.getId() != null) {
            target.setId(source.getId());
        }
        target.setTitle(source.getTitle());
        target.setLeaderId(source.getLeader().getId());
        target.setLeaderName(source.getLeader().getFullName());
        target.setConferenceId(source.getConference().getId());
        return target;
    }
}
