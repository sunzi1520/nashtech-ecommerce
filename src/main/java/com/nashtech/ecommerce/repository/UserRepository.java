package com.nashtech.ecommerce.repository;

import com.nashtech.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    boolean existsById(Long userId);

    Boolean existsByEmail(String email);

    List<User> findAllByUsername(String username);
}
