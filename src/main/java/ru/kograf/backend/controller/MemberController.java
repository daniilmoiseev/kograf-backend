package ru.kograf.backend.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kograf.backend.dto.ConferenceDto;
import ru.kograf.backend.dto.JobDto;
import ru.kograf.backend.dto.SectionDto;
import ru.kograf.backend.dto.UserDto;
import ru.kograf.backend.dto.UserWithPasswordDto;
import ru.kograf.backend.service.ConferenceService;
import ru.kograf.backend.service.JobService;
import ru.kograf.backend.service.UserService;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final UserService userService;
    private final JobService jobService;
    private final ConferenceService conferenceService;

    @GetMapping("/conferences")
    public List<ConferenceDto> getAllConferences() {
        log.debug("Get all conferences");
        return conferenceService.getConferencesPublic();
    }

    @GetMapping("/conference/{id}")
    public ConferenceDto getConference(@PathVariable Long id) {
        log.debug("Get conference {}", id);
        return conferenceService.getConference(id);
    }

    @GetMapping("/conference/{id}/sections")
    public List<SectionDto> getSectionsByConference(@PathVariable Long id) {
        log.debug("Get sections by conferences {}", id);
        return conferenceService.getSections(id);
    }

    @GetMapping("/getUser")
    public UserDto getUser(String email) {
        return userService.getUser(email);
    }

    @PutMapping("/profile/update")
    public UserDto updateProfile(@RequestBody UserDto userDto) {
        log.debug("Update profile {}", userDto.getId());
        return userService.updateUser(userDto);
    }

    @DeleteMapping("/profile/delete")
    public void deleteProfile(@RequestParam Long id) {
        log.debug("Delete profile {}", id);
        userService.deleteUser(id);
    }

    @PutMapping("/profile/password")
    public UserDto updatePassword(@RequestBody UserWithPasswordDto userDto) {
        log.debug("Changed password {}", userDto.getId());
        return userService.updatePassword(userDto);
    }

    //TODO add & delete profile picture

    @GetMapping("/jobs/{userId}")
    public List<JobDto> getJobs(@PathVariable Long userId) {
        log.debug("Get jobs by user {}", userId);
        return jobService.getJobsByUser(userId);
    }

    @GetMapping("/jobs/{userId}/{jobId}")
    public JobDto getOneJob(@PathVariable Long userId, @PathVariable Long jobId) {
        log.debug("Get job by user {} and job {}", userId, jobId);
        return jobService.getJob(userId, jobId);
    }

    @PostMapping("/jobs")
    public JobDto createJob(@RequestBody JobDto jobDto) {
        log.debug("Create job {}", jobDto.getTitle());
        return jobService.createJob(jobDto);
    }

    //TODO send comments

    //TODO send link for activation
}
