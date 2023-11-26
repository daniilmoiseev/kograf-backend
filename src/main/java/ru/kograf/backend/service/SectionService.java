package ru.kograf.backend.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kograf.backend.conversation.service.IKografConversionService;
import ru.kograf.backend.dto.SectionDto;
import ru.kograf.backend.model.Section;
import ru.kograf.backend.repository.SectionRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class SectionService {

    private final IKografConversionService conversionService;
    private final SectionRepository sectionRepository;

    public List<SectionDto> getSections() {
        List<Section> sectionsFromDb = sectionRepository.findAll();
        return conversionService.convert(sectionsFromDb, SectionDto.class);
    }

    public SectionDto createSection(Long conferenceId, SectionDto sectionDto) {
        Section section = conversionService.convert(sectionDto, Section.class);
        if (section != null) {
            Section saved = sectionRepository.save(section);

            return conversionService.convert(saved, SectionDto.class);
        } else {
            log.warn("Unable to convert section {}", sectionDto.getTitle());
        }

        return null;
    }

}
