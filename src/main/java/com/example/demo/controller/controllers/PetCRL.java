package com.example.demo.controller.controllers;

import com.example.demo.service.PetService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user/pet")
public class PetCRL {

    @Autowired
    UserService userService;

    @Autowired
    private PetService petService;

    @GetMapping("/list-pets")
    public String index(Model model, HttpSession session){
        try {
            model=userService.getUserLogin(session,model);
            List<com.example.demo.model.entity.Pet> listPets = petService.findAllPetBySession(session);
            model.addAttribute("listPets",listPets);
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return "listPet";
    }
//    @GetMapping("/create-pet")
//    public String createPet(){
//
//    }
}
