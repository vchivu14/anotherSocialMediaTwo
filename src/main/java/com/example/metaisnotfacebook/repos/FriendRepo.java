package com.example.metaisnotfacebook.repos;

import com.example.metaisnotfacebook.entities.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepo extends JpaRepository<Friend,Integer> {
    Friend findByEmailAndHostAndUsersId(String email, String host, Integer userId);
    List<Friend> findAllByUsersId(int userId);
}
