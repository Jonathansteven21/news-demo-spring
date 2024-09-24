package com.egg.news.repositories;

import com.egg.news.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Author entities.
 * Extends JpaRepository for basic CRUD operations.
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, String> {

    /**
     * Custom query method to search for authors by name.
     *
     * @param name The name to search for (case-insensitive).
     * @return List of authors matching the provided name.
     */
    @Query("SELECT a FROM Author a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Author> searchByName(@Param("name") String name);

}
