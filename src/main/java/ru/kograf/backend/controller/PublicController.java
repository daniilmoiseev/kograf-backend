package ru.kograf.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kograf.backend.service.ConferenceService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor
public class PublicController {

    private final ConferenceService conferenceService;
}
