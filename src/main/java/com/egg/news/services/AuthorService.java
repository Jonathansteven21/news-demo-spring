package com.egg.news.services;

import com.egg.news.entities.Author;
import com.egg.news.exceptions.UserInputException;
import com.egg.news.repositories.AuthorRepository;
import com.egg.news.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing Author entities.
 * Handles business logic related to authors and interacts with the AuthorRepository.
 */
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    /**
     * Creates a new author with the provided name.
     *
     * @param name The name of the author.
     * @throws UserInputException If input validation fails.
     */
    @Transactional
    public void createAuthor(String name) throws UserInputException {
        ValidationUtils.validateInput(name);

        Author author = new Author();
        author.setName(name);
        authorRepository.save(author);
    }

    /**
     * Retrieves a list of all authors.
     *
     * @return List of all authors.
     */
    public List<Author> listAuthors() {
        return authorRepository.findAll();
    }

    /**
     * Updates the name of the author with the provided ID.
     *
     * @param id   The ID of the author to be updated.
     * @param name The new name for the author.
     * @throws UserInputException If input validation fails or the author ID is not found.
     */
    @Transactional
    public void setAuthor(String id, String name) throws UserInputException {
        try {
            ValidationUtils.validateInput(id, name);

            Optional<Author> optionalAuthor = authorRepository.findById(id);
            Author author = optionalAuthor.orElseThrow(() -> new UserInputException("Author ID not found"));

            author.setName(name);
            authorRepository.save(author);
        } catch (UserInputException e) {
            e.handle();
        }
    }

    /**
     * Retrieves the author with the provided ID.
     *
     * @param id The ID of the author to be retrieved.
     * @return The author entity.
     */
    public Author getOne(String id) {
        return authorRepository.getReferenceById(id);
    }

    /**
     * Searches for authors by name.
     *
     * @param name The name to search for (case-insensitive).
     * @return List of authors matching the provided name.
     */
    public List<Author> searchAuthor(String name) {
        if (!ValidationUtils.isInvalidInput(name)) {
            return authorRepository.searchByName(name);
        }

        return listAuthors();
    }
}
