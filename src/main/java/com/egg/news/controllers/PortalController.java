package com.egg.news.controllers;

import com.egg.news.entities.User;
import com.egg.news.exceptions.UserInputException;
import com.egg.news.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class PortalController {

    private final UserService userService;

    @Autowired
    public PortalController(UserService userService) {
        this.userService = userService;
    }


    // Method to add success message to the model
    private void addSuccessMessage(ModelMap modelMap, String statusMessage) {
        modelMap.put("success", "The User has been " + statusMessage + " successfully!");
    }

    // Method to add error message to the model
    private void addErrorMessage(ModelMap modelMap, String messageError) {
        modelMap.put("error", messageError);
    }

    // Controller for handling requests related to the portal's root URL ("/")

    // Display the index (home) page when the root URL is accessed
    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/register")
    public String register() {
        return "registry.html";
    }

    @PostMapping("/registry")
    public String registry(@RequestParam String name, @RequestParam String email, @RequestParam String password, @RequestParam String password2, ModelMap modelMap, MultipartFile file) {
        try {
            userService.register(file,name, email, password, password2);
            addSuccessMessage(modelMap, "added");
            return "index.html";
        } catch (UserInputException e) {
            addErrorMessage(modelMap, e.getMessage());
            modelMap.addAttribute("name", name);
            modelMap.addAttribute("email", email);
            return "registry.html";
        }
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelMap) {
        if (error != null) {
            addErrorMessage(modelMap, "User or password invalids");
        }
        return "login.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/home")
    public String home(HttpSession session) {

        User logged = (User) session.getAttribute("usersession");


        if(logged.getRole().toString().equals("ADMIN")){
            return "redirect:/admin/dashboard";
        }

        return "home.html";
    }
}
