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

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode records = mapper.readTree(new File("data.json"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        for (JsonNode node : records) {
            Photo photo = new Photo();
            photo.setFriendlyName(node.get("id").asText());
            photo.setThumbName(node.get("thumb").asText());
            photo.setLargeName(node.get("large").asText());
            photo.setOriginalName(node.get("originalName").asText());
            photo.setLat(node.get("lat").asDouble());
            photo.setLon(node.get("lng").asDouble());
            photo.setCaption(node.get("caption").asText());
            photo.setDate(LocalDateTime.parse(node.get("date").asText(), formatter).toLocalDate());

            photoRepository.save(photo);
        }

        System.out.println("Migration complete. " + photoRepository.count() + " photos inserted.");

    }

}
