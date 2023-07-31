package com.example.demo.controller.controllers;


import com.example.demo.model.entity.Image;
import com.example.demo.model.entity.User;
import com.example.demo.security.AuthenticationFacade;
import com.example.demo.service.ImageService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private UserService userService;


    public User getUser() {
        Authentication authentication = authenticationFacade.getAuthentication();
        return userService.findByEmail((String) authentication.getPrincipal());
    }

    // upload ảnh
    @PostMapping("/files")
    public Image uploadImage(@ModelAttribute("file") MultipartFile file) {
        // upload len user id 1;
        return imageService.uploadImageByUserId(getUser().getId(),file);
    }

    @PostMapping("/file-pet/{id}")
    public Image uploadImagePet(@ModelAttribute("file") MultipartFile file,@PathVariable("id") Integer id) {
        return imageService.uploadImageByPetId(id,file);
    }


    // xem ảnh
    @GetMapping("/files/{id}")
    public ResponseEntity<?> readImage(@PathVariable Integer id) {
        Image image = imageService.readImageById(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getType()))
                .body(image.getData());
    }

    @GetMapping("/users/{id}/files")
    public ResponseEntity<?> getImageByUserId(@PathVariable Integer id) {

        Image image;
        try {
            image = imageService.getImageListByUserId(id).get(0);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.toString());
        }
        return  ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getType()))
                .body(image.getData());

    }


    // 3. xoá ảnh
    @DeleteMapping("/files/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable(name = "id") Integer Fileid) {
        // đang đăng nhập user id 1;
        imageService.deleteImageById(Fileid, 1);
        return ResponseEntity.noContent().build();
    }

}