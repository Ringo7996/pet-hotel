package com.example.demo.service;

import com.example.demo.model.entity.Image;
import com.example.demo.model.entity.User;
import com.example.demo.repository.ImageRepository;
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
            Image image2upload = Image.builder()
                    .type(file.getContentType())
                    .data(file.getBytes())
                    .user(user2upload)
                    .build();

            imageRepository.save(image2upload);
            return image2upload;
        } catch (IOException e) {
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
}
