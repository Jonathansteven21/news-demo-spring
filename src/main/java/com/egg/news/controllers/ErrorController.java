package com.egg.news.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping(value = "/error",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView renderErrorPage(HttpServletRequest httpServletRequest){

        ModelAndView errorPage= new ModelAndView("error");

        String errorMsg="";

        int httpErrorCode= getErrorCode(httpServletRequest);
        errorMsg = switch (httpErrorCode) {
            case 400 -> "The requested resource does not exist.";
            case 403 -> "You do not have permission to access this resource.";
            case 401 -> "Access denied due to invalid or missing credentials.";
            case 404 -> "Resource not found.";
            case 500 -> "An unexpected error occurred.";
            default -> errorMsg;
        };

        errorPage.addObject("code",httpErrorCode);
        errorPage.addObject("message",errorMsg);
        return errorPage;
    }

    private int getErrorCode(HttpServletRequest httpServletRequest) {
        Integer errorCode = (Integer) httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        return (errorCode != null) ? errorCode : 500;
    }


    public String getErrorPath(){
        return "/error.html";
    }
}
