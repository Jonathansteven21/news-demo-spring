package com.egg.news.repositories;

import com.egg.news.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Author entities.
 * Extends JpaRepository for basic CRUD operations.
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, String> {


}
