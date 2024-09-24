package com.egg.news.services;

import com.egg.news.entities.Author;
import com.egg.news.entities.News;
import com.egg.news.exceptions.UserInputException;
import com.egg.news.repositories.AuthorRepository;
import com.egg.news.repositories.NewsRepository;
import com.egg.news.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Service class for managing News entities.
 * Handles business logic related to news articles and interacts with the NewsRepository and AuthorRepository.
 */
@Service
public class NewsService {

    private final NewsRepository newsRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository, AuthorRepository authorRepository) {
        this.newsRepository = newsRepository;
        this.authorRepository = authorRepository;
    }

    /**
     * Creates a new news article with the provided title, body, and author ID.
     *
     * @param title    The title of the news article.
     * @param body     The body/content of the news article.
     * @param idAuthor The ID of the author for the news article.
     * @throws UserInputException If input validation fails or the author ID is not found.
     */
    @Transactional
    public void createNews(String title, String body, String idAuthor) throws UserInputException {
        ValidationUtils.validateInput(title, body, idAuthor);

        Author author = authorRepository.findById(idAuthor)
                .orElseThrow(() -> new UserInputException("Author ID not found"));

        News news = new News();
        news.setTitle(title);
        news.setBody(body);
        news.setDate(new Date());
        news.setAuthor(author);

        newsRepository.save(news);
    }

    /**
     * Retrieves a list of all news articles.
     *
     * @return List of all news articles.
     */
    public List<News> listNews() {
        return newsRepository.getListNews();
    }

    /**
     * Searches for news articles based on the provided title and/or author ID.
     * If both title and author ID are provided, it performs an AND search.
     *
     * @param title    The title to search for (case-insensitive).
     * @param idAuthor The ID of the author to filter news articles.
     * @return List of news articles matching the provided criteria.
     */
    public List<News> searchNews(String title, String idAuthor) {
        if (!ValidationUtils.isInvalidInput(title) && !ValidationUtils.isInvalidInput(idAuthor)) {
            return newsRepository.searchByTitleAndAuthor(title, idAuthor);
        }
        if (!ValidationUtils.isInvalidInput(title)) {
            return newsRepository.searchByTitle(title);
        }
        if (!ValidationUtils.isInvalidInput(idAuthor)) {
            return newsRepository.searchByAuthor(idAuthor);
        }
        return listNews();
    }

    /**
     * Updates the details of a news article with the provided ID.
     *
     * @param id       The ID of the news article to be updated.
     * @param title    The new title for the news article.
     * @param body     The new body/content for the news article.
     * @param idAuthor The new author ID for the news article.
     * @throws UserInputException If input validation fails or the news article ID is not found.
     */
    @Transactional
    public void setNews(String id, String title, String body, String idAuthor) throws UserInputException {
        ValidationUtils.validateInput(id, title, body, idAuthor);

        News news = newsRepository.findById(id).orElseThrow(() -> new UserInputException("News ID not found"));
        Author author = authorRepository.findById(idAuthor).orElse(news.getAuthor());

        news.setTitle(title);
        news.setBody(body);
        news.setAuthor(author);

        newsRepository.save(news);
    }

    /**
     * Retrieves a specific news article by its ID.
     *
     * @param id The ID of the news article to be retrieved.
     * @return The news article entity.
     */
    public News getOne(String id) {
        return newsRepository.getReferenceById(id);
    }

    /**
     * Soft deletes a news article by setting its status to false.
     *
     * @param id The ID of the news article to be deleted.
     * @throws UserInputException If the news article ID is not found.
     */
    @Transactional
    public void deleteNews(String id) throws UserInputException {
        ValidationUtils.validateInput(id);

        News news = newsRepository.findById(id).orElseThrow(() -> new UserInputException("News ID not found"));

        news.setStatus(false);
        newsRepository.save(news);
    }
}
