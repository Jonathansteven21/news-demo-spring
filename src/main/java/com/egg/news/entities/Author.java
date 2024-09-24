package com.egg.news.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

/**
 * Entity class representing an Author in the application.
 * Uses JPA annotations for persistence and Hibernate-specific annotations for ID generation.
 */
@Entity
public class Author {

    // ID field with UUID generation strategy
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    // Name of the author
    private String name;

    /**
     * Default constructor for JPA compliance.
     * Should be present even if it's empty.
     */
    public Author() {
        // No-arg constructor for JPA
    }


    /**
     * Getter method for retrieving the author's ID.
     *
     * @return The ID of the author.
     */
    public String getId() {
        return id;
    }

    /**
     * Setter method for setting the author's ID.
     *
     * @param id The ID to set for the author.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter method for retrieving the author's name.
     *
     * @return The name of the author.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for setting the author's name.
     *
     * @param name The name to set for the author.
     */
    public void setName(String name) {
        this.name = name;
    }
}
