package com.example.demo.service;

import com.example.demo.model.entity.Image;
import com.example.demo.model.entity.Pet;
import com.example.demo.model.entity.User;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.PetRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImp implements ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PetRepository petRepository;

    @Override
    public Image readImageById(Integer id) {
        Optional<Image> image = imageRepository.findById(id);
        if (!image.isPresent()) {
            throw new RuntimeException("id not found");
        }
        return image.get();
    }

    @Override
    public void deleteImageById(Integer imageId, Integer userId) {
        Image image2find = imageRepository.findById(imageId).orElseThrow(()->{
            throw new RuntimeException("file id not found");
        });

        if(image2find.getId().equals(userId)){
            throw new RuntimeException("cant delete other user's file");
        }
        imageRepository.delete(image2find);
    }

    @Override
    public Image uploadImageByUserId(Integer userId, MultipartFile file) {
        validateFile(file);
        User user2upload = userRepository.findById(userId)
                .orElseThrow(() -> {
                    throw new RuntimeException("user id not found");
                });
        try {
            List<Image> image = getImageListByUserId(userId);
            if(!image.isEmpty()) {
                Image image1 = image.get(0);
                image1.setType(file.getContentType());
                image1.setData(file.getBytes());
                imageRepository.save(image1);
                return  image1;
            }

            Image image2upload = Image.builder()
                    .type(file.getContentType())
                    .data(file.getBytes())
                    .user(user2upload)
                    .build();
            imageRepository.save(image2upload);
            user2upload.setImage(image2upload);
            userRepository.save(user2upload);

            return image2upload;
        } catch (IOException e) {
            System.out.println(e.toString());
            throw new RuntimeException("Upload error");
        }
    }

    @Override
    public Image uploadImageByPetId(Integer petId, MultipartFile file) {
        validateFile(file);
        Pet pet2find = petRepository.findById(petId)
                .orElseThrow(() -> {
                    throw new RuntimeException("Pet id not found");
                });
        try {
            Optional<Image> image = getImageByPetId(petId);
            if(image.isPresent()) {
                image.get().setType(file.getContentType());
                image.get().setData(file.getBytes());
                imageRepository.save(image.get());
                return  image.get();
            }

            Image image2upload = Image.builder()
                    .type(file.getContentType())
                    .data(file.getBytes())
                    .pet(pet2find)
                    .build();
            imageRepository.save(image2upload);
            pet2find.setImage(image2upload);
            petRepository.save(pet2find);
            return image2upload;

        } catch (IOException e) {
            System.out.println(e.toString());
            throw new RuntimeException("Upload error");
        }
    }


    private void validateFile(MultipartFile file) {
        String[] IMAGE_TYPES = {"image/jpeg", "image/png", "image/gif"};

        boolean typeValidate = false;
        for (String type : IMAGE_TYPES) {
            if (file.getContentType().equals(type)) {
                typeValidate = true;
            }
        }

        if (!typeValidate) {
            throw new RuntimeException("File type is not allowed");
        }
    }


    @Override
    public List<Image> getImageListByUserId(Integer id) {
        User user2find = userRepository.findById(id)
                .orElseThrow(() -> {
                    throw new RuntimeException("user id not found");
                });
        return imageRepository.findByUser_Id(id);
    }

    @Override
    public Optional<Image> getImageByPetId(Integer id){
        Pet pet2find = petRepository.findById(id)
                .orElseThrow(() -> {
                    throw new RuntimeException("Pet id not found");
                });

        return imageRepository.findByPet_Id(id);
    }
}
