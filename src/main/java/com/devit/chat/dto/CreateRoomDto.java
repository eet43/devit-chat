package com.devit.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CreateRoomDto {
    @Column(columnDefinition = "BINARY(16)")
    private UUID boardId;

    @Column(columnDefinition = "BINARY(16)")
    private UUID receiverId;

    private String senderName;
    private String receiverName;

}

