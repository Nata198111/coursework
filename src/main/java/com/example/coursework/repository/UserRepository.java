package com.example.coursework.repository;
import com.example.coursework.models.Role;
import com.example.coursework.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findFirstByRole(Role role);
}
