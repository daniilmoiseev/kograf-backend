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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kograf.backend.dto.ConferenceDto;
import ru.kograf.backend.dto.JobDto;
import ru.kograf.backend.dto.UserDto;
import ru.kograf.backend.model.enums.Role;
import ru.kograf.backend.model.enums.UserStatus;
import ru.kograf.backend.service.ConferenceService;
import ru.kograf.backend.service.JobService;
import ru.kograf.backend.service.UserService;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final JobService jobService;
    private final ConferenceService conferenceService;
    private final UserService userService;

    @PostMapping("/conference/create")
    //@PreAuthorize("hasAnyAuthority('ADMIN_PERMISSION')")
    public ConferenceDto createConference(@RequestBody ConferenceDto conferenceDto) {
        log.debug("Create conference {}", conferenceDto.getTitle() != null ? conferenceDto.getTitle() : "unknown");
        return conferenceService.createConference(conferenceDto);
    }

    @PutMapping("/conference/{id}/update")
    //@PreAuthorize("hasAnyAuthority('ADMIN_PERMISSION')")
    public ConferenceDto updateConference(@PathVariable Long id, @RequestBody ConferenceDto conferenceDto) {
        log.debug("Update conference {}", conferenceDto.getTitle() != null ? conferenceDto.getTitle() : "unknown");
        return conferenceService.updateConference(id, conferenceDto);
    }

    @GetMapping("/conference/{id}/users")
    //@PreAuthorize("hasAnyAuthority('ADMIN_PERMISSION')")
    public Long countUsersOnConference(@PathVariable Long id) {
        log.debug("Get count users for conference {}", id);
        return conferenceService.countUsersOnConference(id);
    }

    @GetMapping("/jobs/{conferenceId}")
    public List<JobDto> getJobs(@PathVariable Long conferenceId) {
        log.debug("Get jobs by conference {}", conferenceId);
        return jobService.getJobsByConference(conferenceId);
    }

    @PostMapping("/user/changestatus")
    public boolean changeStatus(@RequestParam Long userId, @RequestParam UserStatus status) {
        log.debug("Change status {} for user {}", status.name(), userId);
        return userService.changeStatus(userId, status);
    }

    @PostMapping("/user/appointrole")
    public boolean appointRole(@RequestParam Long userId, @RequestParam Role role) {
        log.debug("Appoint role {} for user {}", role.getName(), userId);
        return userService.appointRole(userId, role);
    }

    @GetMapping("/user/getAdmins")
    public List<UserDto> getAdmins() {
        log.debug("Get admins");
        return userService.getAdmins();
    }

    @PostMapping("/conference/{id}/appointadmin")
    public void appointAdmin(@PathVariable Long id, @RequestParam Long userId) {
        log.debug("Appoint admin {} for conference {}", userId, id);
        conferenceService.appointAdmin(id, userId);
    }

    @PostMapping("/conference/{id}/disappointadmin")
    public void disappointAdmin(@PathVariable Long id) {
        log.debug("Disappoint admin for conference {}", id);
        conferenceService.disappointAdmin(id);
    }

    @GetMapping("/getAllUsers")
    public List<UserDto> getAllUsers() {
        log.debug("Get all users");
        return userService.getAllUsers();
    }
}
