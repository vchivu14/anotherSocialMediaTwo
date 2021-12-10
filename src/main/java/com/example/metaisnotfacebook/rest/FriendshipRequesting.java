package com.example.metaisnotfacebook.rest;

import com.example.metaisnotfacebook.dtos.FriendshipProtocolRequest;
import com.example.metaisnotfacebook.dtos.FriendshipProtocolResponse;
import com.example.metaisnotfacebook.services.FriendshipService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/response")
public class FriendshipRequesting {
    final String SERVER_B_API = "http://localhost:8080/api/friendship";

    private FriendshipService friendshipService;

    public FriendshipRequesting(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    private ClientHttpConnector connector() {
        return new ReactorClientHttpConnector(HttpClient.create(ConnectionProvider.newConnection()));
    }

    @PostMapping
    private ResponseEntity<FriendshipProtocolResponse> sendFriendshipRequest
            (@RequestBody @Valid FriendshipProtocolRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            FriendshipProtocolResponse response = new FriendshipProtocolResponse(request.getVersion(), 503, "Wrong parameters!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            if (friendshipService.checkSentFriendshipStatus(request) != null) {
                return new ResponseEntity<>(friendshipService.checkSentFriendshipStatus(request),HttpStatus.OK);
            } else {
                WebClient webClient = WebClient.builder()
                        .clientConnector(connector())
                        .baseUrl(SERVER_B_API)
                        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .build();

                FriendshipProtocolResponse response = webClient.post()
                        .body(Mono.just(request), FriendshipProtocolRequest.class)
                        .retrieve()
                        .bodyToMono(FriendshipProtocolResponse.class)
                        .block();

                if (response != null) {
                    if (response.getStatus() == 200) {
                        response = friendshipService.solveMethodByResponse(request);
                    }
                } else {
                    response = new FriendshipProtocolResponse(request.getVersion(), 500, "Some error occurred!");
                }
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
    }
}
