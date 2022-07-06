package com.devit.chat.dto;

import lombok.Data;

@Data
public class Message {
    public enum MessageStatus {
        ENTER, COMM
    }

    private MessageStatus messageStatus;
    private String roomId;
    private String sender;
    private String message;
}
