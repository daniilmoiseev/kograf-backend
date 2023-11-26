package ru.kograf.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kograf.backend.model.Conference;

public interface ConferenceRepository extends JpaRepository<Conference, Long> {

}
