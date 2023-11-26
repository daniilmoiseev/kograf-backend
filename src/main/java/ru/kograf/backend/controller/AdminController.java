package ru.kograf.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kograf.backend.service.ConferenceService;
import ru.kograf.backend.service.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final ConferenceService conferenceService;

    /*@GetMapping("/getReturnAbility")
    @PreAuthorize("hasAnyAuthority('ADMIN_PERMISSION')")
    public long getReturnAbility(String employeeName,
            String carWashLocation,
            @DateTimeFormat(pattern = "dd-MM-yyyy") Date start,
            @DateTimeFormat(pattern = "dd-MM-yyyy") Date end) {
        return adminService.getReturnAbility(employeeName, carWashLocation, start, end);
    }*/

}
