package com.egg.news.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

/**
 * Entity class representing News in the application.
 * Uses JPA annotations for persistence and Hibernate-specific annotations for ID generation.
 */
@Entity
public class News {

    // ID field with UUID generation strategy
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    // Title of the news
    private String title;

    // Body content of the news, with TEXT column definition
    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    // Status of the news (true for visible, false for hidden)
    private Boolean status;

    // Date when the news was created or last updated
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    // Many-to-One relationship with Author entity
    @ManyToOne
    private Author author;

    /**
     * Default constructor for JPA compliance.
     * Initializes the status to true by default.
     */
    public News() {
        status = true;
    }

    /**
     * Getter method for retrieving the news ID.
     *
     * @return The ID of the news.
     */
    public String getId() {
        return id;
    }

    /**
     * Setter method for setting the news ID.
     *
     * @param id The ID to set for the news.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter method for retrieving the news title.
     *
     * @return The title of the news.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter method for setting the news title.
     *
     * @param title The title to set for the news.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter method for retrieving the news body.
     *
     * @return The body content of the news.
     */
    public String getBody() {
        return body;
    }

    /**
     * Setter method for setting the news body.
     *
     * @param body The body content to set for the news.
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Getter method for retrieving the news status.
     *
     * @return The status of the news (true for visible, false for hidden).
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * Setter method for setting the news status.
     *
     * @param status The status to set for the news.
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * Getter method for retrieving the news date.
     *
     * @return The date when the news was created or last updated.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Setter method for setting the news date.
     *
     * @param date The date to set for the news.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Getter method for retrieving the author of the news.
     *
     * @return The Author entity associated with the news.
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * Setter method for setting the author of the news.
     *
     * @param author The Author entity to set for the news.
     */
    public void setAuthor(Author author) {
        this.author = author;
    }
}
