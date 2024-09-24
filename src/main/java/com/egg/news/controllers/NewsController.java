package com.egg.news.controllers;

import com.egg.news.entities.Author;
import com.egg.news.entities.News;
import com.egg.news.exceptions.UserInputException;
import com.egg.news.services.AuthorService;
import com.egg.news.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;
    private final AuthorService authorService;

    // Paths for HTML templates
    private static final String NEWS_LIST_PATH = "./news/news_list.html";
    private static final String NEWS_CREATE_PATH = "./news/news_create.html";
    private static final String NEWS_UPDATE_PATH = "./news/news_update.html";
    private static final String NEWS_SEARCH_PATH = "./news/news_search.html";

    @Autowired
    public NewsController(AuthorService authorService, NewsService newsService) {
        this.newsService = newsService;
        this.authorService = authorService;
    }

    // Method to add success message to the model
    private void addSuccessMessage(ModelMap modelMap, String statusMessage) {
        modelMap.put("success", "The News has been " + statusMessage + " successfully!");
    }

    // Method to add error message to the model
    private void addErrorMessage(ModelMap modelMap, String messageError) {
        modelMap.put("error", messageError);
    }

    // Method to add the list of authors to the model
    private void addAuthorList(ModelMap modelMap) {
        List<Author> authors = authorService.listAuthors();
        modelMap.addAttribute("authors", authors);
    }

    // Redirect to the base URL
    @GetMapping("/")
    public String redirectUrl() {
        return "redirect:/news";
    }

    // Display the main menu
    @GetMapping("")
    public String menu() {
        return "./news/news_menu.html";
    }

    // Display the news registration form
    @GetMapping("/register")
    public String register(ModelMap modelMap) {
        addAuthorList(modelMap);
        return NEWS_CREATE_PATH;
    }

    // Handle news creation
    @PostMapping("/create")
    public String create(@RequestParam String title, @RequestParam String body, @RequestParam String idAuthor, ModelMap modelMap) {
        try {
            // Create news using the provided title, body, and author ID
            newsService.createNews(title, body, idAuthor);
            addSuccessMessage(modelMap, "added");
            return "index.html";
        } catch (UserInputException e) {
            // Handle exception and display error message
            addErrorMessage(modelMap, e.getMessage());
            addAuthorList(modelMap);
            return NEWS_CREATE_PATH;
        }
    }

    // Display the list of news
    @GetMapping("/list")
    public String getList(ModelMap modelMap) {
        // Retrieve and add the list of news to the model
        List<News> news = newsService.listNews();
        modelMap.addAttribute("news", news);
        return NEWS_LIST_PATH;
    }

    // Display the update form for a specific news
    @GetMapping("/set/{id}")
    public String update(@PathVariable String id, ModelMap modelMap) {
        // Retrieve and add the specific news to the model
        modelMap.put("news", newsService.getOne(id));
        addAuthorList(modelMap);
        return NEWS_UPDATE_PATH;
    }

    // Handle news update
    @PostMapping("/UPDATE/{id}")
    public String update(@PathVariable String id, @RequestParam String title, @RequestParam String body, @RequestParam String idAuthor, ModelMap modelMap) {
        try {
            // Update news using the provided title, body, and author ID
            newsService.setNews(id, title, body, idAuthor);
            addSuccessMessage(modelMap, "updated");
            return "redirect:../list";
        } catch (UserInputException e) {
            // Handle exception and display error message
            addErrorMessage(modelMap, e.getMessage());
            modelMap.put("news", newsService.getOne(id));
            addAuthorList(modelMap);
            return NEWS_UPDATE_PATH;
        }
    }

    // Handle news deletion
    @PostMapping("/DELETE/{id}")
    public String delete(@PathVariable String id, ModelMap modelMap) {
        try {
            // Delete news using the provided ID
            newsService.deleteNews(id);
            addSuccessMessage(modelMap, "delete");
        } catch (UserInputException e) {
            // Handle exception and display error message
            addErrorMessage(modelMap, e.getMessage());
        }
        return "redirect:../list";
    }

    // Display the search form
    @GetMapping("/search")
    public String search(ModelMap modelMap) {
        addAuthorList(modelMap);
        return NEWS_SEARCH_PATH;
    }

    // Handle news search
    @GetMapping("/GET")
    public String search(@RequestParam String title, @RequestParam String idAuthor, ModelMap modelMap) {
        // Retrieve and add the list of news matching the provided title and author ID to the model
        modelMap.addAttribute("news", newsService.searchNews(title, idAuthor));
        addAuthorList(modelMap);
        return NEWS_SEARCH_PATH;
    }
}
