package ru.kograf.backend.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
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
import ru.kograf.backend.service.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final JobService jobService;
    private final ConferenceService conferenceService;

    @PostMapping("/conference/create")
    //@PreAuthorize("hasAnyAuthority('ADMIN_PERMISSION')")
    public ConferenceDto createConference(@RequestBody ConferenceDto conferenceDto) {
        if (conferenceDto != null) {
            return conferenceService.createConference(conferenceDto);
        }
        return null;
    }

    @PutMapping("/conference/{id}/update")
    //@PreAuthorize("hasAnyAuthority('ADMIN_PERMISSION')")
    public ConferenceDto updateConference(@PathVariable Long id, @RequestBody ConferenceDto conferenceDto) {
        if (conferenceDto != null) {
            return conferenceService.updateConference(id, conferenceDto);
        }
        return null;
    }

    @GetMapping("/conferences")
    public List<ConferenceDto> getConferences() {
        return conferenceService.getConferencesAdmin();
    }

    @GetMapping("/jobs/{conferenceId}")
    public List<JobDto> getJobs(@PathVariable Long conferenceId) {
        return jobService.getJobsByConference(conferenceId);
    }

    //TODO download job

    /*@GetMapping("/getReturnAbility")
    @PreAuthorize("hasAnyAuthority('ADMIN_PERMISSION')")
    public long getReturnAbility(String employeeName,
            String carWashLocation,
            @DateTimeFormat(pattern = "dd-MM-yyyy") Date start,
            @DateTimeFormat(pattern = "dd-MM-yyyy") Date end) {
        return adminService.getReturnAbility(employeeName, carWashLocation, start, end);
    }*/

}
