package com.devit.chat.service;

import com.devit.chat.dto.CreateRoomDto;
import com.devit.chat.entity.ChatRoom;
import com.devit.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    public UUID save(CreateRoomDto createRoomDto) {
        ChatRoom chatRoom = ChatRoom.createChatRoom(createRoomDto.getName());
        return chatRoomRepository.save(chatRoom);
    }

    public ChatRoom findById(UUID uuid) {
        return chatRoomRepository.findByUUID(uuid);
    }

    public List<ChatRoom> findAll() {
        return chatRoomRepository.findAllRooms();
    }

}
