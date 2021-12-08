package com.example.metaisnotfacebook.rest;

import com.example.metaisnotfacebook.dtos.FriendshipProtocolRequest;
import com.example.metaisnotfacebook.dtos.FriendshipProtocolResponse;
import com.example.metaisnotfacebook.services.FriendshipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/friendship")
public class FriendshipIncoming {
    private FriendshipService friendshipService;

    public FriendshipIncoming(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @PostMapping
    public ResponseEntity<FriendshipProtocolResponse> receiveFriendshipRequest
            (@RequestBody FriendshipProtocolRequest request) {
        FriendshipProtocolResponse response = friendshipService.receiveFriendshipRequest(request);
        System.out.println(response.getPhrase());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
