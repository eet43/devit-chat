package com.devit.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import java.util.UUID;

@Data
@AllArgsConstructor
public class EnterRoomDto {
    @Column(columnDefinition = "BINARY(16)")
    private UUID roomId;
}

