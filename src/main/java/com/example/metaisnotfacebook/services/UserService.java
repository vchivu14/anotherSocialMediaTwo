package com.example.metaisnotfacebook.services;

import com.example.metaisnotfacebook.dtos.UserDTO;
import com.example.metaisnotfacebook.dtos.UserInformationDTO;
import com.example.metaisnotfacebook.entities.User;

public interface UserService {
    boolean getUserByEmail(String email);
    User login(UserDTO user);
    User signup(User user);
    UserInformationDTO getUsersInformation(int userId);
    User getById(int userId);
}
