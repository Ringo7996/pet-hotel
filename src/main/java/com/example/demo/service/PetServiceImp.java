package com.example.demo.service;

import com.example.demo.exception.ForbiddenException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.entity.Pet;
import com.example.demo.model.entity.User;
import com.example.demo.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PetServiceImp implements PetService {
    @Autowired
    private PetRepository petRepository;

    @Override
    public Pet findById(Integer petId) {
        return petRepository.findById(petId).orElseThrow(() -> new NotFoundException("Không tìm thấy pet với id " + petId));
    }

    @Override
    public Pet getOnePet(Integer petId, User loginUser) {
        Pet pet = findById(petId);
        if (loginUser.getAuthorities().contains("ROLE_ROOT_ADMIN") || loginUser.getAuthorities().contains("ROLE_HOTEL_STAFF")) {
            return pet;
        }

        if (loginUser.getPets().contains(pet) && pet.isStatus()) {
            return pet;
        } else {
            throw new ForbiddenException("Bạn không thể truy cập pet này");
        }
    }

    @Override
    public Page<Pet> getAllPetsWithPage(Pageable pageable) {
        return petRepository.findAll(pageable);
    }
}



