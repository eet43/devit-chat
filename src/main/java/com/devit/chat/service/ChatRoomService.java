package com.devit.chat.service;

import com.devit.chat.dto.CreateRoomDto;
import com.devit.chat.dto.EnterRoomDto;
import com.devit.chat.entity.ChatRoom;
import com.devit.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.server.UID;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 1. 채팅방 생성
 * 2. 채팅방 입장
 * 3. 채팅방 존재하는지 검사
 * 4. 채팅방 목록 반환
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    public Optional<ChatRoom> createRoom(UUID uuid, CreateRoomDto createRoomDto) {
        log.info("{}", createRoomDto);
        UUID senderId = uuid;
        UUID receiverId = createRoomDto.getReceiverId();

        // 유저 2명의 ID 를 통해, 이미 존재하는지 아닌지 확인해야함. 존재하면 해당 채팅방 리턴.

        Optional<ChatRoom> findRooms = check(senderId, receiverId);

        if (findRooms.isEmpty()) {
            //기존 방이 없으면 두 유저간의 새로운 채팅방 생성 (새로운 유저의 이직서)
            ChatRoom chatRoom = chatRoomRepository.save(ChatRoom.createChatRoom(createRoomDto.getRoomName(), senderId, receiverId));
            return Optional.ofNullable(chatRoom);
        } else {
            //기존 방이 있으면 기존 채팅방 생성 (기존 유저의 이직서)
            return findRooms;
        }
    }

    public Optional<ChatRoom> enterRoom(EnterRoomDto enterRoomDto) {
        return chatRoomRepository.findByUUID(enterRoomDto.getRoomId());
    }

    private Optional<ChatRoom> check(UUID senderId, UUID receiverId) {

        Optional<ChatRoom> findChatRoom = chatRoomRepository.findByUser(senderId, receiverId);

        return findChatRoom;
    }

    public Optional<List<ChatRoom>> findAllRoom(UUID userId) {
        return chatRoomRepository.findAllRooms(userId);
    }

}
