package com.example.metaisnotfacebook.configuration;

import com.example.metaisnotfacebook.entities.User;
import com.example.metaisnotfacebook.repos.UserRepo;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {
    private UserRepo userRepo;

    public DataLoader(UserRepo userRepo) {
        this.userRepo = userRepo;
        if (userRepo.count() == 0) {
            loadUser();
        }
    }

    private void loadUser() {
        userRepo.save(new User("simon", "hola"));
    }
}
