package com.example.metaisnotfacebook.services;

import com.example.metaisnotfacebook.dtos.FriendshipProtocolRequest;
import com.example.metaisnotfacebook.dtos.FriendshipProtocolResponse;
import com.example.metaisnotfacebook.entities.FriendshipRequest;
import com.example.metaisnotfacebook.repos.FriendRepo;
import com.example.metaisnotfacebook.repos.FriendshipRepo;
import com.example.metaisnotfacebook.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendshipServiceImp implements FriendshipService {
    private FriendshipRepo friendshipRepo;
    private FriendRepo friendRepo;
    private UserRepo userRepo;

    @Autowired
    UserService userService;

    public FriendshipServiceImp(FriendshipRepo friendshipRepo, FriendRepo friendRepo, UserRepo userRepo) {
        this.friendshipRepo = friendshipRepo;
        this.friendRepo = friendRepo;
        this.userRepo = userRepo;
    }

    private FriendshipRequest getFriendshipRequestFromDTO(FriendshipProtocolRequest friendshipProtocolRequest) {
        FriendshipRequest friendshipRequest = new FriendshipRequest();
        friendshipRequest.setEmail(friendshipProtocolRequest.getRecipient());
        friendshipRequest.setHost(friendshipProtocolRequest.getRcpHost());
        // "pending", "denied", "accepted"
        friendshipRequest.setStatus("pending");
        return friendshipRequest;
    }

    @Override
    public FriendshipProtocolResponse checkSentFriendshipStatus(FriendshipProtocolRequest friendshipProtocolRequest) {
        // user is sender in the request when sending friendship requests
        int userId = userRepo.findByEmail(friendshipProtocolRequest.getSender()).getId();
        if (friendRepo.findByEmailAndHostAndUsersId(friendshipProtocolRequest.getRecipient(),
                friendshipProtocolRequest.getRcpHost(), userId) != null) {
            return new FriendshipProtocolResponse(friendshipProtocolRequest.getVersion(), 201, "Already friends!");
        } else if (friendshipRepo.findByEmailAndHostAndUsersId(friendshipProtocolRequest.getRecipient(),
                friendshipProtocolRequest.getRcpHost(), userId) != null) {
            return new FriendshipProtocolResponse(friendshipProtocolRequest.getVersion(), 201, "Friend Request already sent!");
        } else {
            return new FriendshipProtocolResponse();
        }
    }

    @Override
    public FriendshipProtocolResponse checkReceivedFriendshipStatus(FriendshipProtocolRequest friendshipProtocolRequest) {
        // user is receiver in the request when receiving friendship requests
        int userId = userRepo.findByEmail(friendshipProtocolRequest.getRecipient()).getId();
        if (friendRepo.findByEmailAndHostAndUsersId(friendshipProtocolRequest.getSender(),
                friendshipProtocolRequest.getSrcHost(), userId) != null) {
            return new FriendshipProtocolResponse(friendshipProtocolRequest.getVersion(), 201, "Already friends!");
        } else if (friendshipRepo.findByEmailAndHostAndUsersId(friendshipProtocolRequest.getSender(),
                friendshipProtocolRequest.getSrcHost(), userId) != null) {
            return new FriendshipProtocolResponse(friendshipProtocolRequest.getVersion(), 201, "Friend Request already sent!");
        } else {
            return new FriendshipProtocolResponse();
        }

    }

    @Override
    public void saveSuccessfulFriendshipRequestResponse(FriendshipProtocolRequest friendshipProtocolRequest) {
        // user is sender in the request when sending friendship requests
        int userId = userRepo.findByEmail(friendshipProtocolRequest.getSender()).getId();
        FriendshipRequest friendshipRequest = getFriendshipRequestFromDTO(friendshipProtocolRequest);
        // true for SENT requests false for RECEIVED ones
        // in this case we SEND a friendship request, so it's true type
        friendshipRequest.setType(true);
        friendshipRequest.setUsersId(userId);
        friendshipRepo.save(friendshipRequest);
    }

    @Override
    public FriendshipProtocolResponse receiveFriendshipRequest(FriendshipProtocolRequest friendshipProtocolRequest) {
        if (!userService.getUserByEmail(friendshipProtocolRequest.getRecipient())) {
            return new FriendshipProtocolResponse(friendshipProtocolRequest.getVersion(), 201, "No user here with this account!");
        } else {
            if (checkReceivedFriendshipStatus(friendshipProtocolRequest).getVersion() != null) {
                return checkReceivedFriendshipStatus(friendshipProtocolRequest);
            } else {
                // user is recipient in the request when receiving friendship requests
                int userId = userRepo.findByEmail(friendshipProtocolRequest.getRecipient()).getId();
                FriendshipRequest friendshipRequest = getFriendshipRequestFromDTO(friendshipProtocolRequest);
                // true for SENT requests false for RECEIVED ones
                // in this case we RECEIVE a friendship request, so it's false type
                friendshipRequest.setType(false);
                friendshipRequest.setUsersId(userId);
                friendshipRepo.save(friendshipRequest);
                return new FriendshipProtocolResponse(friendshipProtocolRequest.getVersion(), 200, "Success");
            }
        }
    }
}
