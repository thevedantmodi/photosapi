package com.vedantmodi.photosapi.controller;

import com.vedantmodi.photosapi.model.Photo;
import com.vedantmodi.photosapi.service.IngestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/ingest")
public class IngestController {

    private final IngestService ingestService;

    public IngestController(IngestService ingestService) {
        this.ingestService = ingestService;
    }

    /* POST /ingest */
    @PostMapping
    public ResponseEntity<Photo> ingest(
            @RequestParam("file") MultipartFile file,
            @RequestParam("caption") String caption
    ) throws IOException {
        Optional<Photo> photo = ingestService.ingest(file, caption);
        return photo.map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }
}