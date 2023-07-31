package com.example.demo.service;


import com.example.demo.model.entity.Pet;
import com.example.demo.model.entity.User;
import com.example.demo.model.request.UpdatePetRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PetService {
    Pet findById(Integer petId);

    List<Pet> findAllPetBySession(HttpSession session);

    Pet getOnePet(Integer petId, User loginUser);

    Page<Pet> getAllPetsWithPage(Pageable pageable);

    void updateInfoPet (UpdatePetRequest pet, Integer id, HttpSession session);
}
