package com.example.metaisnotfacebook.services;

import com.example.metaisnotfacebook.dtos.FriendDTO;
import com.example.metaisnotfacebook.dtos.FriendshipRequestDTO;
import com.example.metaisnotfacebook.dtos.UserDTO;
import com.example.metaisnotfacebook.dtos.UserInformationDTO;
import com.example.metaisnotfacebook.entities.Friend;
import com.example.metaisnotfacebook.entities.FriendshipRequest;
import com.example.metaisnotfacebook.entities.User;
import com.example.metaisnotfacebook.repos.FriendRepo;
import com.example.metaisnotfacebook.repos.FriendshipRepo;
import com.example.metaisnotfacebook.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    private UserRepo userRepo;

    @Autowired
    FriendRepo friendRepo;
    @Autowired
    FriendshipRepo friendshipRepo;

    public UserServiceImp(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User getById(int userId) {
        return userRepo.getById(userId);
    }

    @Override
    public boolean getUserByEmail(String email) {
        return userRepo.findByEmail(email) != null;
    }

    @Override
    public User login(UserDTO user) {
        try {
            User dbUser = userRepo.findByEmail(user.getEmail());
            if (!dbUser.getPassword().equals(user.getPassword())) return null;
            else return dbUser;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public User signup(User user) {
        return userRepo.save(user);
    }

    @Override
    public UserInformationDTO getUsersInformation(int userId) {
        UserInformationDTO userInformationDTO = new UserInformationDTO();

        List<FriendDTO> friendDTOS = new ArrayList<>();
        List<Friend> friendList = friendRepo.findAllByUsersId(userId);
        for (Friend f: friendList) {
            friendDTOS.add(new FriendDTO(f));
        }
        userInformationDTO.setFriends(friendDTOS);

        List<FriendshipRequestDTO> friendshipRequestDTOSent = new ArrayList<>();
        List<FriendshipRequest> friendshipRequestsSent = friendshipRepo.findAllByUsersIdAndType(userId,true);
        for (FriendshipRequest f: friendshipRequestsSent) {
            friendshipRequestDTOSent.add(new FriendshipRequestDTO(f));
        }
        userInformationDTO.setRequestsSent(friendshipRequestDTOSent);

        List<FriendshipRequestDTO> friendshipRequestDTOReceived = new ArrayList<>();
        List<FriendshipRequest> friendshipRequestsReceived = friendshipRepo.findAllByUsersIdAndType(userId,false);
        for (FriendshipRequest f: friendshipRequestsReceived) {
            friendshipRequestDTOReceived.add(new FriendshipRequestDTO(f));
        }
        userInformationDTO.setRequestsReceived(friendshipRequestDTOReceived);

        return userInformationDTO;
    }
}
