package ru.kograf.backend.service;

import com.ibm.icu.text.Transliterator;
import jakarta.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.kograf.backend.config.CommonConfig;
import ru.kograf.backend.exeption.FileNotFoundException;
import ru.kograf.backend.exeption.FileStorageException;
import ru.kograf.backend.model.Conference;
import ru.kograf.backend.model.Job;
import ru.kograf.backend.repository.ConferenceRepository;

@Slf4j
@Service
public class FileService {

    private final Path fileStorageLocation;
    private final ConferenceRepository conferenceRepository;

    public FileService(CommonConfig commonConfig, ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
        this.fileStorageLocation = Paths.get(commonConfig.getUploadPath()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
                    ex);
        }
    }

    @SneakyThrows
    @Transactional(readOnly = true)
    public ResponseEntity<Resource> downloadFilesByConference(Long conferenceId, HttpServletRequest request) {
        log.info("files by conference {}", conferenceId);

        Conference conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new Exception("Unable to find conference by id " + conferenceId));

        List<String> fileNameList = conference.getUsers().stream()
                .map(e -> {
                    List<Job> jobs = e.getJobs();
                    return jobs.stream().map(Job::getFileName).toList();
                })
                .flatMap(List::stream)
                .filter(org.apache.commons.lang3.StringUtils::isNotBlank)
                .map(e -> Arrays.asList(e.split(",")))
                .flatMap(List::stream)
                .toList();

        String fileNameZip = zippingFiles(conference.getTitle(), fileNameList);
        return downloadFile(fileNameZip, request);
    }

    @SneakyThrows
    public ResponseEntity<Resource> downloadFile(String fileName, HttpServletRequest request) {
        log.info("download file by name {}", fileName);

        Resource resource = loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + encodeFileName(resource.getFilename()))
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION)
                .body(resource);
    }

    @SneakyThrows
    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName, ex);
        }
    }

    public String zippingFiles(String conferenceTitle, List<String> fileNameList) {
        String zipFileName = "jobs_" + conferenceTitle + "_"
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss")) + ".zip";
        Path targetLocation = this.fileStorageLocation.resolve(zipFileName);

        try (OutputStream fos = Files.newOutputStream(targetLocation.toAbsolutePath())) {
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            for (String fileName : fileNameList) {
                Path filePath = this.fileStorageLocation.resolve(fileName).normalize();

                if (filePath.toFile().exists()) {
                    File fileToZip = new File(filePath.toAbsolutePath().toString());

                    try (InputStream fis = Files.newInputStream(fileToZip.toPath().toAbsolutePath())) {
                        ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                        zipOut.putNextEntry(zipEntry);

                        byte[] bytes = new byte[1024];
                        int length;
                        while ((length = fis.read(bytes)) >= 0) {
                            zipOut.write(bytes, 0, length);
                        }
                    }
                }

            }
            zipOut.close();
        } catch (IOException e) {
            log.error("Unable to zip files", e);
        }

        return zipFileName;
    }

    private String encodeFileName(String fileName) {
        Transliterator toLatinTrans = Transliterator.getInstance("Russian-Latin/BGN");
        return toLatinTrans.transliterate(fileName);
    }
}
