package com.devit.chat.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class SampleCreateDto {
    private String boardId;
    private String receiverId;
    private String roomName;
}
