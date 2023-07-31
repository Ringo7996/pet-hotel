package com.example.demo.controller.restcontroller;



import com.example.demo.model.entity.Pet;
import com.example.demo.model.request.UpdatePetRequest;
import com.example.demo.service.PetService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/pets")
@PreAuthorize("hasAnyRole('ROLE_ROOT_ADMIN', 'ROLE_HOTEL_STAFF', 'ROLE_HOTEL_ADMIN')")
public class PetController {
    @Autowired
    private PetService petService;

    @GetMapping("/")
    public Page<Pet> getAllPets(Pageable pageable) {
        return petService.getAllPetsWithPage(pageable);
    }

    @PostMapping("/update-info-pet/{id}")
    public ResponseEntity<?> updateInfoPet(
            @PathVariable("id") Integer id,
            @RequestBody UpdatePetRequest request,
            HttpSession session)
    {
        try {
            petService.updateInfoPet(request,id,session);
        } catch (Exception e){
            System.out.println(e.toString());
            return ResponseEntity.badRequest().body(e);
        }
        return ResponseEntity.ok("Update success");
    }

}
