package com.example.demo.controller.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class Home {

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("page","index");
        return "comom";
    }

}
