package com.example.demo.controller.restcontroller;



import com.example.demo.model.entity.Pet;
import com.example.demo.model.request.UpdatePetRequest;
import com.example.demo.service.PetService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/pets")
public class PetController {
    @Autowired
    private PetService petService;

    @PreAuthorize("hasAnyRole('ROLE_ROOT_ADMIN', 'ROLE_ADMIN')")
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

    @PostMapping("/add-new-pet")
    public  ResponseEntity<?> addNewPet(@Valid @ModelAttribute UpdatePetRequest petRequest,
                                            @RequestParam(value = "file",required = false) MultipartFile file,HttpSession session ){


        try {
            petService.saveNewPet(petRequest,file, session);
        } catch (Exception e){
            System.out.println(e.toString());
            return ResponseEntity.badRequest().body(e.toString());
        }
        return ResponseEntity.ok("Add success");

    }

}
