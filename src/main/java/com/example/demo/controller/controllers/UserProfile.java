package com.example.demo.controller.controllers;

import com.example.demo.model.entity.User;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserProfile {
    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String index (Model model, HttpSession session){
        String email = (String) session.getAttribute("MY_SESSION");
        try {
            User user = userService.findByEmail(email);
            model.addAttribute("USER",user);
       } catch (Exception e){
           System.out.println(e.toString());
       }
        return "web/user/profile";
    }

    @GetMapping("/update-password")
    public String updatePassword(HttpSession session,Model model){
        String email = (String) session.getAttribute("MY_SESSION");
        try {
            User user = userService.findByEmail(email);
            model.addAttribute("USER",user);
        } catch (Exception e){
            System.out.println(e.toString());
        }

        return "web/user/update-password";
    }

}
