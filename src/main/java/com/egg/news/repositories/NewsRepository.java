package com.egg.news.repositories;

import com.egg.news.entities.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing News entities.
 * Extends JpaRepository for basic CRUD operations.
 */
@Repository
public interface NewsRepository extends JpaRepository<News, String> {

    /**
     * Custom query method to search for news by title.
     *
     * @param title The title to search for (case-insensitive).
     * @return List of news matching the provided title.
     */
    @Query("SELECT n FROM News n WHERE n.status = true AND LOWER(n.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<News> searchByTitle(@Param("title") String title);

    /**
     * Custom query method to search for news by author ID.
     *
     * @param idAuthor The ID of the author to search for.
     * @return List of news authored by the provided author.
     */
    @Query("SELECT n FROM News n WHERE n.status = true AND n.author.id = :idAuthor")
    List<News> searchByAuthor(@Param("idAuthor") String idAuthor);

    /**
     * Custom query method to search for news by title and author ID.
     *
     * @param title    The title to search for (case-insensitive).
     * @param idAuthor The ID of the author to search for.
     * @return List of news matching the provided title and authored by the provided author.
     */
    @Query("SELECT n FROM News n WHERE n.status = true AND LOWER(n.title) LIKE LOWER(CONCAT('%', :title, '%')) AND n.author.id = :idAuthor")
    List<News> searchByTitleAndAuthor(@Param("title") String title, @Param("idAuthor") String idAuthor);

    /**
     * Custom query method to get a list of all news with status set to true.
     *
     * @return List of all news with status set to true.
     */
    @Query("SELECT n FROM News n WHERE n.status = true")
    List<News> getListNews();
}
