package com.egg.news.controllers;

import com.egg.news.entities.Author;
import com.egg.news.exceptions.UserInputException;
import com.egg.news.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // Paths for HTML templates
    private static final String AUTHOR_LIST_PATH = "./author/author_list.html";
    private static final String AUTHOR_CREATE_PATH = "./author/author_create.html";
    private static final String AUTHOR_UPDATE_PATH = "./author/author_update.html";
    private static final String AUTHOR_SEARCH_PATH = "./author/author_search.html";

    // Method to add success message to the model
    private void addSuccessMessage(ModelMap modelMap, String statusMessage) {
        modelMap.put("success", "The Author has been " + statusMessage + " successfully!");
    }

    // Method to add error message to the model
    private void addErrorMessage(ModelMap modelMap, String messageError) {
        modelMap.put("error", messageError);
    }

    // Redirect to the base URL
    @GetMapping("/")
    public String redirectUrl() {
        return "redirect:/author";
    }

    // Display the main menu
    @GetMapping("")
    public String menu() {
        return "./author/author_menu.html";
    }

    // Display the registration form
    @GetMapping("/register")
    public String register() {
        return AUTHOR_CREATE_PATH;
    }

    // Handle author creation
    @PostMapping("/create")
    public String create(@RequestParam String firstName, @RequestParam String lastName, ModelMap modelMap) {
        try {
            // Create author using the provided name
            authorService.createAuthor(firstName + " " + lastName);
            addSuccessMessage(modelMap, "added");
            return "index.html";
        } catch (UserInputException e) {
            // Handle exception and display error message
            addErrorMessage(modelMap, e.getMessage());
            return AUTHOR_CREATE_PATH;
        }
    }

    // Display the list of authors
    @GetMapping("/list")
    public String getList(ModelMap modelMap) {
        // Retrieve and add the list of authors to the model
        List<Author> authors = authorService.listAuthors();
        modelMap.addAttribute("authors", authors);
        return AUTHOR_LIST_PATH;
    }

    // Display the update form for a specific author
    @GetMapping("/set/{id}")
    public String update(@PathVariable String id, ModelMap modelMap) {
        // Retrieve and add the specific author to the model
        modelMap.put("author", authorService.getOne(id));
        return AUTHOR_UPDATE_PATH;
    }

    // Handle author update
    @PostMapping("/set/{id}")
    public String update(@PathVariable String id, @RequestParam String name, ModelMap modelMap) {
        try {
            // Update author using the provided name
            authorService.setAuthor(id, name);
            addSuccessMessage(modelMap, "updated");
            return "redirect:../list";
        } catch (UserInputException e) {
            // Handle exception and display error message
            addErrorMessage(modelMap, e.getMessage());
            modelMap.put("author", authorService.getOne(id));
            return AUTHOR_UPDATE_PATH;
        }
    }

    // Display the search form
    @GetMapping("/search")
    public String search(ModelMap modelMap) {
        return AUTHOR_SEARCH_PATH;
    }

    // Handle author search
    @GetMapping("/searching")
    public String search(@RequestParam String name, ModelMap modelMap) {
        // Retrieve and add the list of authors matching the provided name to the model
        modelMap.addAttribute("authors", authorService.searchAuthor(name));
        return AUTHOR_SEARCH_PATH;
    }

}
