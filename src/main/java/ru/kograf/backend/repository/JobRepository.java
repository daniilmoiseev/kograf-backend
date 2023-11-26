package ru.kograf.backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.kograf.backend.model.Job;

public interface JobRepository extends JpaRepository<Job, Long> {

}
