package ru.kograf.backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.kograf.backend.dto.UploadFileResponse;
import ru.kograf.backend.service.FileService;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping(value = "/downloadFiles/{conferenceId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadFiles(@PathVariable Long conferenceId,
            HttpServletRequest request) {
        log.debug("Download file by conference {}", conferenceId);
        return fileService.downloadFilesByConference(conferenceId, request);
    }

    @GetMapping(value = "/downloadFile/{fileName:.+}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        log.debug("Download file by name {}", fileName);
        return fileService.downloadFile(fileName, request);
    }

    @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UploadFileResponse uploadFile(@RequestParam MultipartFile file) {
        log.debug("Upload one file {}", file.getOriginalFilename());
        String fileName = fileService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/files")
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping(value = "/uploadMultipleFiles", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<UploadFileResponse> uploadFiles(@RequestParam MultipartFile[] files) {
        log.debug("Upload multiple files");
        return Arrays.stream(files)
                .map(this::uploadFile)
                .collect(Collectors.toList());
    }
}
