package com.example.demo.controller.controllers;

import com.example.demo.model.entity.User;
import com.example.demo.service.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class Home {
    @Autowired
    UserServiceImpl userService;
    @GetMapping("")
    public String index(Model model,  HttpSession session){
        String email =(String) session.getAttribute("MY_SESSION");
        try {
            User user = userService.findByEmail(email);
            model.addAttribute("USER",user);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        model.addAttribute("page","index");
        return "common";
    }

}
