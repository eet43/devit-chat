package com.devit.chat.service;

import com.devit.chat.dto.CreateRoomDto;
import com.devit.chat.entity.ChatRoom;
import com.devit.chat.repository.ChatRoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChatRoomServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    ChatRoomService chatRoomService;

    @Autowired
    ChatRoomRepository chatRoomRepository;

    @Test
    @Rollback(value = false)
    @Transactional
    public void 채팅방_생성() throws Exception {
        //given
        CreateRoomDto createRoomDto = new CreateRoomDto(UUID.randomUUID(), UUID.randomUUID());
        System.out.println(createRoomDto);

        //when
        ChatRoom chatRoom = ChatRoom.createChatRoom(createRoomDto.getSenderId(), createRoomDto.getReceiverId());
        System.out.println(chatRoom);
        UUID uuid = chatRoomRepository.save(chatRoom);
        //then
        assertEquals(chatRoom, chatRoomRepository.findByUUID(uuid));

    }

    @Test
    @Rollback(value = false)
    @Transactional
    public void 채팅방_체크_이미_존재() throws Exception {
        //given
        CreateRoomDto createRoomDto = new CreateRoomDto(UUID.randomUUID(), UUID.randomUUID());
        ChatRoom chatRoom = ChatRoom.createChatRoom(createRoomDto.getSenderId(), createRoomDto.getReceiverId());
        UUID uuid = chatRoomRepository.save(chatRoom);

        //when


        //then
    }
}