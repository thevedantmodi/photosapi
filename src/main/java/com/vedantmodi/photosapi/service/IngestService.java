package com.vedantmodi.photosapi.service;

import com.vedantmodi.photosapi.model.Photo;
import com.vedantmodi.photosapi.repository.PhotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class IngestService {

    private final ExifService exifService;
    private final R2Service r2Service;
    private final SizerService sizerService;
    private final PhotoRepository photoRepository;

    public IngestService(ExifService exifService, R2Service r2Service,
            SizerService sizerService, PhotoRepository photoRepository) {
        this.exifService = exifService;
        this.r2Service = r2Service;
        this.sizerService = sizerService;
        this.photoRepository = photoRepository;
    }

    public Optional<Photo> ingest(MultipartFile file, String caption)
            throws IOException {
        if (file == null)
            return Optional.empty();

        File tempFile = File.createTempFile("upload-",
                file.getOriginalFilename());
        if (tempFile == null)
            return Optional.empty();
 
        file.transferTo(tempFile);

        String originalName = file.getOriginalFilename();
        if (originalName == null)
            return Optional.empty();

        String baseName = originalName.substring(0,
                originalName.lastIndexOf('.'));
        String ext = originalName.substring(originalName.lastIndexOf('.'));
        String contentType = Files.probeContentType(tempFile.toPath());

        Double lat = null;
        Double lon = null;
        LocalDateTime date = null;

        try {
            Optional<ExifService.Coordinates> coords = exifService
                    .extractCoords(tempFile);
            Optional<LocalDateTime> exifDate = exifService
                    .extractDate(tempFile);

            if (coords.isPresent()) {
                lat = coords.get().lat();
                lon = coords.get().lon();
            }
            if (exifDate.isPresent()) {
                date = exifDate.get();
            }
        } catch (Exception e) {
            System.out.println(
                    "WARN: Could not extract EXIF data for " + originalName);
        }

        byte[] thumbBytes = sizerService.resizeToThumbnail(tempFile);
        byte[] largeBytes = sizerService.resizeToLarge(tempFile);

        String thumbUrl = r2Service.upload(thumbBytes,
                "photos/" + baseName + "_thumb" + ext, contentType);
        String largeUrl = r2Service.upload(largeBytes,
                "photos/" + baseName + "_large" + ext, contentType);

        tempFile.delete();

        Photo photo = new Photo();
        photo.setOriginalName(originalName);
        photo.setFriendlyName(baseName);
        photo.setCaption(caption);
        photo.setLat(lat);
        photo.setLon(lon);
        photo.setDate(date);
        photo.setThumbName(thumbUrl);
        photo.setLargeName(largeUrl);

        return Optional.of(photoRepository.save(photo));
    }
}