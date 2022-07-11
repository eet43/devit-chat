package com.devit.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateRoomDto {
    private String senderId;
    private String receiverId;
    private String roomName;

}

