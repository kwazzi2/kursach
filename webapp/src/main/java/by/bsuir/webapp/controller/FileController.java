package by.bsuir.webapp.controller;

import by.bsuir.webapp.model.tutor.Tutor;
import by.bsuir.webapp.repository.tutor.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
public class FileController {
    @Autowired
    TutorRepository tutorRepository;

    @GetMapping("/download")
    public ResponseEntity<Resource> download() {
        List<Tutor> tutorList = tutorRepository.findAll(TutorRepository.Specs.noHidden());
        StringBuilder builder = new StringBuilder();
        tutorList.forEach(t -> builder.append(t.toString()).append("\n"));
        byte[] data = builder.toString().getBytes(StandardCharsets.UTF_8);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=file.txt");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(data.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new ByteArrayResource(data));
    }
}
