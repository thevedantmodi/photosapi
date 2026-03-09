package com.vedantmodi.photosapi.service;

import org.junit.jupiter.api.Test;

import com.vedantmodi.photosapi.service.ExifService.Coordinates;

import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ExifServiceTest {
    private ExifService exifService;

    @BeforeEach
    void setUp() {
        exifService = new ExifService();
    }

    @Test
    void extractsCoordsFromImageWithGps() throws Exception {
        File image = new File(getClass().getClassLoader()
                .getResource("taipei-skyline-03.png").getFile());
        Optional<Coordinates> coords = exifService.extractCoords(image);
        // assertTrue(coords.lat().isPresent());
        // assertTrue(coords.isEmpty());
        assertTrue(!coords.get().lat().isNaN());
        assertTrue(!coords.get().lon().isNaN());
        System.out.println(String.format("lat: %.4f, lon: %.4f\n",
                coords.get().lat(), coords.get().lon()));
    }

    @Test
    void extractGPSSpeedFromPlanePic() throws Exception {
        File image = new File(getClass().getClassLoader()
                .getResource("tahoe-fromair.png").getFile());
        Optional<Coordinates> coords = exifService.extractCoords(image);
        assertTrue(!coords.get().lat().isNaN());
        assertTrue(!coords.get().lon().isNaN());
        System.out.println(String.format("lat: %.4f, lon: %.4f\n",
                coords.get().lat(), coords.get().lon()));
    }

    @Test
    void extractsDateFromImageWithGps() throws Exception {
        File image = new File(getClass().getClassLoader()
                .getResource("taipei-skyline-03.png").getFile());
        Optional<LocalDate> dt = exifService.extractDate(image);
        assertTrue(!dt.isEmpty());
        System.out.println(String.format("date: %s\n", dt.get().toString()));
    }

}
