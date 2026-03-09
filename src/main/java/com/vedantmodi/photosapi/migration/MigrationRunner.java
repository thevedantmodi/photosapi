package com.vedantmodi.photosapi.migration;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vedantmodi.photosapi.model.Photo;
import com.vedantmodi.photosapi.repository.PhotoRepository;

@Component
@Profile("migrate")
public class MigrationRunner implements CommandLineRunner {
    private final PhotoRepository photoRepository;

    public MigrationRunner(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    private void setOrWarn(Photo photo, String fieldName, String value, Runnable setter) {
        if (value != null && !value.isEmpty()) {
            setter.run();
        } else {
            System.out.println("WARN [" + photo.getFriendlyName() + "]: missing " + fieldName);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode records = mapper.readTree(new File("data.json"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        for (JsonNode json_record : records) {
            Photo photo = new Photo();
            /* set the fields and WARN on empty */
            setOrWarn(photo, "friendlyName", json_record.get("id").asText(),
                    () -> photo.setFriendlyName(json_record.get("id").asText()));
            setOrWarn(photo, "thumbName", json_record.get("thumb").asText(),
                    () -> photo.setThumbName(json_record.get("thumb").asText()));
            setOrWarn(photo, "largeName", json_record.get("large").asText(),
                    () -> photo.setLargeName(json_record.get("large").asText()));
            setOrWarn(photo, "originalName", json_record.get("originalName").asText(),
                    () -> photo.setOriginalName(json_record.get("originalName").asText()));
            setOrWarn(photo, "lat", json_record.get("lat").asText(),
                    () -> photo.setLat(json_record.get("lat").asDouble()));
            setOrWarn(photo, "lon", json_record.get("lng").asText(),
                    () -> photo.setLon(json_record.get("lng").asDouble()));
            setOrWarn(photo, "caption", json_record.get("caption").asText(),
                    () -> photo.setCaption(json_record.get("caption").asText()));
            setOrWarn(photo, "date", json_record.get("date").asText(),
                    () -> photo
                            .setDate(LocalDateTime.parse(json_record.get("date").asText(), formatter).toLocalDate()));

            photoRepository.save(photo);
        }

        System.out.println("Migration complete. " + photoRepository.count() + " photos inserted.");

    }

}
