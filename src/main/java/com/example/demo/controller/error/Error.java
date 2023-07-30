package com.example.demo.controller.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Error implements ErrorController {
    private static final String PATH = "/Error";
    @RequestMapping(PATH)
    public String error() {
        return "Error/error-page" ;
    }

}
