package com.example.metaisnotfacebook.services;


import com.example.metaisnotfacebook.dtos.FriendshipProtocolRequest;
import com.example.metaisnotfacebook.dtos.FriendshipProtocolResponse;

public interface FriendshipService {
    void saveSuccessfulFriendshipRequestResponse(FriendshipProtocolRequest friendshipProtocolRequest);
    FriendshipProtocolResponse receiveFriendshipRequest(FriendshipProtocolRequest friendshipProtocolRequest);
    FriendshipProtocolResponse checkSentFriendshipStatus(FriendshipProtocolRequest friendshipProtocolRequest);
    FriendshipProtocolResponse checkReceivedFriendshipStatus(FriendshipProtocolRequest friendshipProtocolRequest);
}
