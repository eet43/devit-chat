package com.devit.chat.dto;

import com.devit.chat.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class SendMessageDto {
    private ChatRoom room;
    private String message;
}
