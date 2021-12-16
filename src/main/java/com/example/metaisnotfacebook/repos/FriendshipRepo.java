package com.example.metaisnotfacebook.repos;

import com.example.metaisnotfacebook.entities.FriendshipRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendshipRepo extends JpaRepository<FriendshipRequest, Integer> {
    FriendshipRequest findByEmailAndHostAndUsersIdAndType(String email, String host, int userId, Boolean type);

    List<FriendshipRequest> findAllByUsersIdAndType(int userId, boolean type);
}
