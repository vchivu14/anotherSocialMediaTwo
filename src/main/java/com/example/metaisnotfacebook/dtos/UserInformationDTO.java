package com.example.metaisnotfacebook.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInformationDTO {
    List<FriendDTO> friends;
    List<FriendshipRequestDTO> requestsSent;
    List<FriendshipRequestDTO> requestsReceived;
}
