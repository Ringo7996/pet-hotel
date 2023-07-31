package com.example.demo.service;

import com.example.demo.exception.ForbiddenException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.entity.Pet;
import com.example.demo.model.entity.User;
import com.example.demo.model.enums.Sex;
import com.example.demo.model.request.UpdatePetRequest;
import com.example.demo.repository.PetRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetServiceImp implements PetService {
    @Autowired
    private PetRepository petRepository;

    @Autowired
    UserService userService;

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

        if (loginUser.getPets().contains(pet) && pet.isVisible()) {
            return pet;
        } else {
            throw new ForbiddenException("Bạn không thể truy cập pet này");
        }
    }

    @Override
    public Page<Pet> getAllPetsWithPage(Pageable pageable) {
        return petRepository.findAll(pageable);
    }

    @Override
    public List<Pet> findAllPetBySession(HttpSession session) {

        String email = (String) session.getAttribute("MY_SESSION");
        User user = userService.findByEmail(email);

        List<Pet> listPets = user.getPets();

        if(listPets.isEmpty()) throw new RuntimeException("User don't have pet");
        return listPets;
    }

    @Override
    public void updateInfoPet(UpdatePetRequest pet, Integer id, HttpSession session) {

        String email = (String) session.getAttribute("MY_SESSION");
        User user = userService.findByEmail(email);

        Pet petSystem = getOnePet(id, user);

        petSystem.setName(pet.getName());
        petSystem.setBreed(pet.getBreed());
        petSystem.setColor(pet.getColor());
        petSystem.setType(pet.getType());
        if(pet.getSex().equalsIgnoreCase("FEMALE")){
            petSystem.setSex(Sex.FEMALE);
        }else{
            petSystem.setSex(Sex.MALE);
        }

        petRepository.save(petSystem);

    }
}



