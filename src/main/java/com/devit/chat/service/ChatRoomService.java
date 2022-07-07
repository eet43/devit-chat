package com.devit.chat.service;

import com.devit.chat.dto.CreateRoomDto;
import com.devit.chat.entity.ChatRoom;
import com.devit.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    public UUID save(CreateRoomDto createRoomDto) {
        ChatRoom chatRoom = ChatRoom.createChatRoom(createRoomDto.getSenderId(), createRoomDto.getReceiverId());
        return chatRoomRepository.save(chatRoom);
    }

    private List<ChatRoom> check(UUID senderId, UUID receiverId) {
        return chatRoomRepository.findByUserUUID(senderId, receiverId);
    }

    public ChatRoom findById(UUID uuid) {
        return chatRoomRepository.findByUUID(uuid);
    }

    public List<ChatRoom> findAll() {
        return chatRoomRepository.findAllRooms();
    }

}
