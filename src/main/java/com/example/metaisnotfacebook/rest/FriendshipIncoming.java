package com.example.metaisnotfacebook.rest;

import com.example.metaisnotfacebook.dtos.FriendshipProtocolRequest;
import com.example.metaisnotfacebook.dtos.FriendshipProtocolResponse;
import com.example.metaisnotfacebook.services.FriendshipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/friendship")
public class FriendshipIncoming {
    private FriendshipService friendshipService;

    public FriendshipIncoming(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @PostMapping
    public ResponseEntity<FriendshipProtocolResponse> receiveFriendshipRequest
            (@RequestBody @Valid FriendshipProtocolRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            FriendshipProtocolResponse response = new FriendshipProtocolResponse(request.getVersion(), 503, "Wrong parameters!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            FriendshipProtocolResponse response = friendshipService.receiveFriendshipRequest(request);
            System.out.println(response.getPhrase());
            System.out.println(response.getStatus());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
