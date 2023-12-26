package ru.kograf.backend.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kograf.backend.dto.ConferenceDto;
import ru.kograf.backend.dto.JobDto;
import ru.kograf.backend.service.ConferenceService;
import ru.kograf.backend.service.JobService;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final JobService jobService;
    private final ConferenceService conferenceService;

    @PostMapping("/conference/create")
    //@PreAuthorize("hasAnyAuthority('ADMIN_PERMISSION')")
    public ConferenceDto createConference(@RequestBody ConferenceDto conferenceDto) {
        log.debug("Create conference {}", conferenceDto.getTitle());
        if (conferenceDto != null) {
            return conferenceService.createConference(conferenceDto);
        }
        return null;
    }

    @PutMapping("/conference/{id}/update")
    //@PreAuthorize("hasAnyAuthority('ADMIN_PERMISSION')")
    public ConferenceDto updateConference(@PathVariable Long id, @RequestBody ConferenceDto conferenceDto) {
        log.debug("Update conference {}", conferenceDto.getTitle());
        if (conferenceDto != null) {
            return conferenceService.updateConference(id, conferenceDto);
        }
        return null;
    }

    @GetMapping("/conferences")
    public List<ConferenceDto> getConferences() {
        log.debug("Get all conferences");
        return conferenceService.getConferencesAdmin();
    }

    @GetMapping("/jobs/{conferenceId}")
    public List<JobDto> getJobs(@PathVariable Long conferenceId) {
        log.debug("Get jobs by conference {}", conferenceId);
        return jobService.getJobsByConference(conferenceId);
    }
}
