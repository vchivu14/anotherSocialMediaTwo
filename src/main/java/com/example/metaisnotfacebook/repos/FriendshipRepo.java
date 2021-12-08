package com.example.metaisnotfacebook.repos;

import com.example.metaisnotfacebook.entities.FriendshipRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendshipRepo extends JpaRepository<FriendshipRequest, Integer> {
    FriendshipRequest findByEmailAndHostAndUsersId(String email, String host, int userId);
}
