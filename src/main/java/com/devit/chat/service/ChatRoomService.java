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
        UUID senderId = uuid;
        UUID receiverId = createRoomDto.getReceiverId();

        // 유저 2명의 ID 를 통해, 이미 존재하는지 아닌지 확인해야함. 존재하면 해당 채팅방 리턴.

        log.info("ChatRoom(serivce) : 이미 존재하는 채팅방인지 확인합니다.");

        Optional<ChatRoom> findRooms = check(senderId, receiverId);

        if (findRooms.isEmpty()) {
            //기존 방이 없으면 두 유저간의 새로운 채팅방 생성 (새로운 유저의 이직서)
            ChatRoom chatRoom = chatRoomRepository.save(ChatRoom.createChatRoom(createRoomDto.getSenderName(), createRoomDto.getReceiverName(),
                    createRoomDto.getBoardId(), senderId, receiverId));
            log.info("ChatRoom(serivce) : {} 새로운 채팅방을 반환합니다.", chatRoom);

            return Optional.ofNullable(chatRoom);
        } else {
            //기존 방이 있으면 기존 채팅방 생성 (기존 유저의 이직서)
            log.info("ChatRoom(serivce) : {} 기존 채팅방을 반환합니다.", findRooms);
            return findRooms;
        }
    }

    public Optional<ChatRoom> enterRoom(EnterRoomDto enterRoomDto) {

        log.info("ChatRoom(service) : {} 채팅방에 입장합니다.", enterRoomDto);

        return chatRoomRepository.findByUUID(enterRoomDto.getRoomId());
    }

    private Optional<ChatRoom> check(UUID senderId, UUID receiverId) {

        Optional<ChatRoom> findChatRoom = chatRoomRepository.findByUser(senderId, receiverId);

        return findChatRoom;
    }

    public Optional<List<ChatRoom>> findAllRoom(UUID userId) {

        Optional<List<ChatRoom>> findRoom = chatRoomRepository.findAllRooms(userId);

        log.info("ChatRoom(service) : {} 모든 채팅방을 반환합니다..", findRoom);

        return findRoom;
    }

}
