package com.example.demo.repository;


import com.example.demo.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Integer id);

    Page<User> findByStatusOrderById(Boolean status, Pageable pageable);

    @Query("SELECT u FROM User u WHERE EXISTS (SELECT r FROM u.roles r WHERE r.name = 'ADMIN' or r.name = 'ROOT_ADMIN' ) AND NOT EXISTS (SELECT h FROM u.myHotels h WHERE h.id = :hotelId)")
    List<User> getAdminNotPartOfHotel(@Param("hotelId") Integer id);

}
