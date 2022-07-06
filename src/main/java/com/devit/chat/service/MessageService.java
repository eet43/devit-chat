package com.devit.chat.service;

import com.devit.chat.entity.ChatRoom;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {


    private final ObjectMapper objectMapper;
    private Map<UUID, ChatRoom> chatRooms;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRoom() {
        return new ArrayList<>(chatRooms.values());
    }

    public ChatRoom findById(UUID roomId) {
        for (ChatRoom value : chatRooms.values()) {
            log.info("{}", value.getRoomId());
        }
        log.info("-----------------");
        return chatRooms.get(roomId);
    }

    public ChatRoom createRoom(String name) {
        ChatRoom chatRoom = ChatRoom.builder()
                .name(name)
                .build();
        chatRooms.put(chatRoom.getRoomId(), chatRoom);
        log.info("채팅방이 생성되었습니다. : ", name);
        return chatRoom;
    }
    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}

