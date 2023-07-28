package com.example.demo.service;


import com.example.demo.model.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    Image readImageById(Integer id);

    void deleteImageById(Integer imageId, Integer userId);

    Image uploadImageByUserId(Integer userId, MultipartFile file);

    List<Image> getImageListByUserId(Integer id);
}
