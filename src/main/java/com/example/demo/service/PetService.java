package com.example.demo.service;


import com.example.demo.model.entity.Pet;
import com.example.demo.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface PetService {
    Pet findById(Integer petId);

    Pet getOnePet(Integer petId, User loginUser);

    Page<Pet> getAllPetsWithPage(Pageable pageable);
}
