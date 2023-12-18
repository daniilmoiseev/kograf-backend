package ru.kograf.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.kograf.backend.model.Section;

public interface SectionRepository extends JpaRepository<Section, Long> {

    List<Section> getSectionsByConferenceId(Long id);
}
