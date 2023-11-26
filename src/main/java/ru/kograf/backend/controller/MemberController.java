package ru.kograf.backend.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kograf.backend.dto.ConferenceDto;
import ru.kograf.backend.dto.UserDto;
import ru.kograf.backend.service.ConferenceService;
import ru.kograf.backend.service.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final ConferenceService conferenceService;
    private final UserService userService;

    @GetMapping("/conferences")
    public List<ConferenceDto> getConferences() {
        return conferenceService.getConferencesPublic();
    }

    @GetMapping("/getUser")
    public UserDto getUser(String email) {
        return userService.getUser(email);
    }

}
