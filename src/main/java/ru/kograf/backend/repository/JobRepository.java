package ru.kograf.backend.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.kograf.backend.model.Conference;
import ru.kograf.backend.model.Job;
import ru.kograf.backend.model.User;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findAllByUser(User user);

    List<Job> findAllByConference(Conference conference);
}
