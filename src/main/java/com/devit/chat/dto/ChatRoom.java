package com.devit.chat.dto;

import com.devit.chat.service.MessageService;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Data
public class ChatRoom {
    private String roomId;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(String roomId) {
        this.roomId = roomId;
    }

    public void handleActions(WebSocketSession session, Message message, MessageService messageService) {
        if(message.getMessageStatus().equals(Message.MessageStatus.ENTER)) {
            sessions.add(session);
            message.setMessage(message.getSender() + "님이 입장했습니다.");
        }
        sendMessage(message, messageService);
    }

    public <T> void sendMessage(T message, MessageService messageService) {
        sessions.parallelStream().forEach(session -> messageService.sendMessage(session, message));
    }
}
