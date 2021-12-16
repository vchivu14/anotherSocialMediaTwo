package com.example.metaisnotfacebook.services;

import com.example.metaisnotfacebook.entities.Friend;
import com.example.metaisnotfacebook.repos.FriendRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendServiceImp implements FriendService {
    private FriendRepo friendRepo;

    public FriendServiceImp(FriendRepo friendRepo) {
        this.friendRepo = friendRepo;
    }

    @Override
    public List<Friend> findAllFriendsForUser(int userId) {
        return friendRepo.findAllByUsersId(userId);
    }
}
