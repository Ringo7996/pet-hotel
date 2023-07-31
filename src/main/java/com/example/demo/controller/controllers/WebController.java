package com.example.demo.controller.controllers;

import com.example.demo.model.entity.User;
import com.example.demo.service.TokenConfirmService;
import com.example.demo.service.UserService;
import com.example.demo.service.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebController {
    @Autowired
    private TokenConfirmService tokenConfirmService;
    @Autowired
    UserService userService;


    @GetMapping("/login")
    public String getLoginPage(Authentication authentication,Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }
//        model.addAttribute("page","login");
        return "login";
    }

    @GetMapping("/forgot-password")
    public String getForgotPasswordPage(HttpSession session, Model model) {
        String email = (String) session.getAttribute("MY_SESSION");
        try {
            User user = userService.findByEmail(email);
            model.addAttribute("USER",user);
        } catch (Exception e){
            System.out.println(e.toString());
        }

        return "forgot-password";
    }


    // -> Gửi email xác nhận quên mật khẩu
    // Trong email có 1 link để xác thực
    @GetMapping("/reset-password/{token}")
    public String getUpdatePasswordPage(@PathVariable("token") String token, Model model, HttpSession session) {

        model = tokenConfirmService.checkToken(token, model);
        boolean isValid = (boolean) model.getAttribute("isValid") ;
        if(!isValid) return "/Error/error-page";
        return "reset-password";
    }

    @GetMapping("/sign-up")
    public String getSingUp(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            System.out.println("redirect");
            return "redirect:/";
        }
//        model.addAttribute("page", "sign-up");
        return "sign-up";
    }

}
