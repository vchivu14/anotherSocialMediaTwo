package com.example.metaisnotfacebook.repos;

import com.example.metaisnotfacebook.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
