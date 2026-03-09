package com.vedantmodi.photosapi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vedantmodi.photosapi.model.Photo;
import com.vedantmodi.photosapi.repository.PhotoRepository;

import lombok.NonNull;

@RestController
@RequestMapping("/photos")
public class PhotoController {

    private final PhotoRepository photoRepository;

    public PhotoController(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    /* GET /photos */
    /* GET /photos?year={year} */
    @GetMapping
    public List<Photo> getAllPhotos(@RequestParam(required = false) Integer year) {
        if (year != null) {
            return photoRepository.findByYear(year);
        }
        return photoRepository.findAll();
    }

    /* GET /photos/{id} */
    // Id must be non NULL
    @GetMapping("/{id}") 
    public Photo getPhotoById(@PathVariable @NonNull Long id) {
        return photoRepository.findById(id).orElseThrow();
    }

}