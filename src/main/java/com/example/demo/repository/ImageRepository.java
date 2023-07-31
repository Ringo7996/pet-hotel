package com.example.demo.repository;


import com.example.demo.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findByUser_Id(Integer id);

    Optional<Image> findByPet_Id(Integer id);
}