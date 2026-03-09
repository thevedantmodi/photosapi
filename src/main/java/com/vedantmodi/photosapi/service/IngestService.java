package com.vedantmodi.photosapi.service;

import com.vedantmodi.photosapi.model.Photo;
import com.vedantmodi.photosapi.repository.PhotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;

@Service
public class IngestService {
    private final ExifService exifService;
    private final R2Service r2Service;
    private final SizerService sizerService;
}
