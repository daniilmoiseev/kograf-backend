package ru.kograf.backend.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.kograf.backend.conversation.service.IKografConversionService;
import ru.kograf.backend.dto.JobDto;
import ru.kograf.backend.model.Conference;
import ru.kograf.backend.model.Job;
import ru.kograf.backend.model.User;
import ru.kograf.backend.repository.ConferenceRepository;
import ru.kograf.backend.repository.JobRepository;
import ru.kograf.backend.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class JobService {

    private final IKografConversionService conversionService;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final ConferenceRepository conferenceRepository;

    @SneakyThrows
    public List<JobDto> getJobsByConference(Long conferenceId) {
        Conference conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new Exception("Unable to find conference by id " + conferenceId));
        List<Job> jobs = jobRepository.findAllByConference(conference);
        return conversionService.convert(jobs, JobDto.class);
    }

    @SneakyThrows
    public List<JobDto> getJobsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("Unable to find user by id " + userId));
        List<Job> jobs = jobRepository.findAllByUser(user);
        return conversionService.convert(jobs, JobDto.class);
    }

    @SneakyThrows
    public JobDto getJob(Long userId, Long jobId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("Unable to find user by id " + userId));
        List<Job> jobs = jobRepository.findAllByUser(user);
        Optional<Job> jopOptional = jobs.stream()
                .filter(e -> e.getId().equals(jobId))
                .findFirst();
        if (jopOptional.isPresent()) {
            Job job = jopOptional.get();
            return conversionService.convert(job, JobDto.class);
        }
        return null;
    }
}
