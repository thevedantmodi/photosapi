/* definition of "Photo" model that will be represented in the database */
package com.vedantmodi.photosapi.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "photos")
@Data /* adds getters and setters for all the private field */
@NoArgsConstructor /* adds a dummy constructor */
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String friendlyName;
    private String thumbName;
    private String largeName;
    private String originalName;
    private Double lat;
    private Double lon;
    private String caption;
    private LocalDate date;

}