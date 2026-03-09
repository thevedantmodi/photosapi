/* definitions for methods that explain how to read and write to the database */
package com.vedantmodi.photosapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vedantmodi.photosapi.model.Photo;

/* Creating a JPA repository with Photo as the value, and Long as an Id */
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    /* A new query that extends the JpaRepository */
    /* Substitutes year into :year */
    @Query("SELECT p FROM Photo p WHERE YEAR(p.date) = :year")
    List<Photo> findByYear(@Param("year") int year);
}