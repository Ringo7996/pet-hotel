package com.example.demo.controller.controllers;

import com.example.demo.service.TokenConfirmService;
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

    // Ai cũng có thể vào được
//    @GetMapping("/")
//    public String getHome() {
//        return "web/index";
//    }

    @GetMapping("/login")
    public String getLoginPage(Authentication authentication,Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }
        model.addAttribute("page","login");
        return "common";
    }

    @GetMapping("/forgot-password")
    public String getForgotPasswordPage() {
        return "forgot-password";
    }



    // -> Gửi email xác nhận quên mật khẩu
    // Trong email có 1 link để xác thực
    @GetMapping("/reset-password/{token}")
    public String getUpdatePasswordPage(@PathVariable String token, Model model) {
        model = tokenConfirmService.checkToken(token, model);
        return "update-password";
    }

    @GetMapping("/sign-up")
    public String getSingUp(Authentication authentication, Model model) {

        if (authentication != null && authentication.isAuthenticated()) {
            System.out.println("redirect");
            return "redirect:/";
        }
        model.addAttribute("page", "sign-up");
        return "common";
    }

}
