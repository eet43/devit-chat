package com.devit.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class SendMessageDto {
    private String roomId; //나중에 UUID 값으로 바꿔야함
    //private String roomId;
    private String message;
}
