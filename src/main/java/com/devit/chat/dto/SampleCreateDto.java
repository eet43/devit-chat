package com.devit.chat.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class SampleCreateDto {

    private String receiverId;
    private String roomName;
}
