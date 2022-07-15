package com.devit.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CreateRoomDto {
    private UUID receiverId;
    private String roomName;
}

