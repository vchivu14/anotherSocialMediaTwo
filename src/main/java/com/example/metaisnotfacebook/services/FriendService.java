package com.example.metaisnotfacebook.services;

import com.example.metaisnotfacebook.entities.Friend;

import java.util.List;

public interface FriendService {
    List<Friend> findAllFriendsForUser(int userId);
}
