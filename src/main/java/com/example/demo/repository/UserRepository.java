package com.example.demo.repository;


import com.example.demo.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Integer id);

    @Query("SELECT u FROM User u WHERE u.id NOT IN (SELECT u2.id FROM User u2 JOIN u2.myHotels h WHERE h.id = :hotelId)")
    List<User> getAdminNotPartOfHotel(@Param("hotelId") Integer id);
}
