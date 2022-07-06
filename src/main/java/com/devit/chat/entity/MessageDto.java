package com.devit.chat.entity;

import lombok.Data;

import java.util.UUID;

@Data
public class MessageDto {
    private UUID roomId;
    private String sender;
    private String message;
}
