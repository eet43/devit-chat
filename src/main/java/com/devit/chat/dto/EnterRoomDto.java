package com.devit.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class EnterRoomDto {
    private UUID roomId;

}

