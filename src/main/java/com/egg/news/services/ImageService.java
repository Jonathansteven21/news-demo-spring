package com.egg.news.services;

import com.egg.news.entities.Author;
import com.egg.news.entities.Image;
import com.egg.news.exceptions.UserInputException;
import com.egg.news.repositories.AuthorRepository;
import com.egg.news.repositories.ImageRepository;
import com.egg.news.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing Author entities.
 * Handles business logic related to authors and interacts with the AuthorRepository.
 */
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    /**
     * Save a new image with the provided name.
     *
     * @param file The MultipartFile of the Image.
     * @throws UserInputException If input validation fails.
     */
    public Image saveImage(MultipartFile file) throws UserInputException {
        ValidationUtils.validateImage(file);
        try {
            Image image = new Image();

            image.setMime(file.getContentType());

            image.setName(file.getName());

            image.setContent(file.getBytes());

            System.out.println(image.toString());

            return imageRepository.save(image);

        } catch (Exception e) {
            throw new UserInputException("The input file is not valid");
        }
    }

    /**
     * Updates the image with the provided ID.
     *
     * @param idImage The ID of the image to be updated.
     * @param file    The new file for the image.
     * @throws UserInputException If input validation fails or the image ID is not found.
     */
    public Image updateImage(MultipartFile file, String idImage) throws UserInputException {
        ValidationUtils.validateImage(file);
        try {

            Optional<Image> optionalImage = imageRepository.findById(idImage);
            Image image = optionalImage.orElseThrow(() -> new UserInputException("Image ID not found"));

            image.setMime(file.getContentType());

            image.setName(file.getName());

            image.setContent(file.getBytes());

            return imageRepository.save(image);

        } catch (Exception e) {
            throw new UserInputException("The input file is not valid");
        }
    }


}
