package com.example.demo.service;


import com.example.demo.model.entity.TokenConfirm;
import com.example.demo.repository.TokenConfirmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TokenConfirmService {
    @Autowired
    private TokenConfirmRepository tokenConfirmRepository;

    public Model checkToken(String token, Model model) {

        // Ktra token co hop le ko, co trong database ko
        Optional<TokenConfirm> tokenConfirmOptional = tokenConfirmRepository.findByToken(token);
        if (tokenConfirmOptional.isEmpty()){
            model.addAttribute("isValid",false);
            model.addAttribute("message","Token is not valid");
            return model;
        }

        TokenConfirm tokenConfirm = tokenConfirmOptional.get();
        // Ktra token da dc kich hoat chua
        if (tokenConfirm.getConfirmedAt() != null){
            model.addAttribute("isValid",false);
            model.addAttribute("message","Token is already actived");
            return model;
        }

        // Ktra token da het han chua
        if (tokenConfirm.getExpiredAt().isBefore(LocalDateTime.now())){
            model.addAttribute("isValid",false);
            model.addAttribute("message","Token is expried");
            return model;
        }

        tokenConfirm.setConfirmedAt(LocalDateTime.now());
        tokenConfirmRepository.save(tokenConfirm);
        model.addAttribute("isValid",true);
        model.addAttribute("email",tokenConfirm.getUser().getEmail());
        return model;
    }
}
