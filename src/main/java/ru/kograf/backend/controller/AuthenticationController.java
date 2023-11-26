package ru.kograf.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kograf.backend.dto.LoginDto;
import ru.kograf.backend.dto.RegistrationDto;
import ru.kograf.backend.dto.UserDto;
import ru.kograf.backend.service.AuthService;
import ru.kograf.backend.service.UserService;

@Slf4j
@RestController
@CrossOrigin(origins = "${spring.whiteip}")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginDto request) {
        return authService.authenticate(request);
    }

    @PostMapping("/registration")
    public UserDto createUser(@RequestBody RegistrationDto registrationDto) {
        log.debug("Registration {}", registrationDto);
        return userService.createUser(registrationDto);
    }

    @PutMapping("/confirmation")
    public Boolean checkConfirmationLink(String email, String link) {
        return userService.checkLink(email, link);
    }
}
