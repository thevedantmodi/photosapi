/* definitions for methods that explain how to read and write to the database */
package com.vedantmodi.photosapi.repository;

import com.vedantmodi.photosapi.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

/* Creating a JPA repository with Photo as the value, and Long as an Id */
public interface PhotoRepository extends JpaRepository<Photo, Long> {

}