package com.vedantmodi.photosapi.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.GpsDirectory;
import com.drew.metadata.exif.ExifSubIFDDirectory;

@Service
public class ExifService {

    public record Coordinates(Double lat, Double lon) {
    }

    public Optional<Coordinates> extractCoords(File file)
            throws ImageProcessingException, IOException {
        Metadata md = ImageMetadataReader.readMetadata(file);
        GpsDirectory gpsInfo = md.getFirstDirectoryOfType(GpsDirectory.class);
        if (gpsInfo == null) {
            return Optional.empty();
        }

        GeoLocation locInfo = gpsInfo.getGeoLocation();
        if (locInfo == null) {
            return Optional.empty();
        }

        Double lat = locInfo.getLatitude();
        Double lon = locInfo.getLongitude();

        return Optional.of(new Coordinates(lat, lon));
    }

    public Optional<LocalDateTime> extractDate(File file)
            throws ImageProcessingException, IOException {
        Metadata md = ImageMetadataReader.readMetadata(file);

        ExifSubIFDDirectory exifDirectory = md
                .getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        if (exifDirectory == null) {
            return Optional.empty();
        }

        String datetimeStr = exifDirectory
                .getString(ExifSubIFDDirectory.TAG_DATETIME_DIGITIZED);
        if (datetimeStr == null)
            return Optional.empty();

        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy:MM:dd HH:mm:ss");

        return Optional.of(LocalDateTime.parse(datetimeStr, formatter));
    }
}
