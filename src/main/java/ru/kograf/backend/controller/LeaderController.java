package ru.kograf.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kograf.backend.service.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class LeaderController {

    private final UserService userService;

    /*@GetMapping("/getUser")
    @PreAuthorize("hasAnyAuthority('CUSTOMER_PERMISSION')")
    public UserDto getUser(String email) {
        return userService.getUser(email);
    }*/
}
