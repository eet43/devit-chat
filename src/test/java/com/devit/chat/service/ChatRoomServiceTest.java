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

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ChatRoomServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    ChatRoomService chatRoomService;

//    @Autowired
//    RoomMemberRepository roomMemberRepository;

    @Autowired
    ChatRoomRepository chatRoomRepository;

//    @Test
//    public void 채팅방_생성() throws Exception {
//        //given
//        CreateRoomDto createRoomDto = new CreateRoomDto(UUID.randomUUID(), UUID.randomUUID());
//        System.out.println(createRoomDto);
//
//        //when
//        UUID roomID = chatRoomService.save(createRoomDto);
//
//        //then
//        System.out.println(chatRoomRepository.findByUUID(roomID));
//
//    }
//
//    @Test
//    public void 채팅방_체크_이미_존재() throws Exception {
//        //given
//        CreateRoomDto createRoomDto = new CreateRoomDto(UUID.randomUUID(), UUID.randomUUID());
//        System.out.println(createRoomDto);
//
//        //when
//        UUID roomID1 = chatRoomService.save(createRoomDto);
//        UUID roomID2 = chatRoomService.save(createRoomDto);
//
//        //then
//        assertEquals(roomID1, roomID2);
//    }

    @Test
    @Rollback(value = false)
    public void 채팅방_생성() throws Exception {
        //given
        UUID senderId = UUID.randomUUID();
        UUID receiverId = UUID.randomUUID();
        String roomName = "김대희";

        CreateRoomDto createRoomDto = new CreateRoomDto();
        createRoomDto.setSenderId(senderId);
        createRoomDto.setReceiverId(receiverId);
        createRoomDto.setRoomName(roomName);

        //when
        Optional<ChatRoom> chatRoom1 = chatRoomService.createRoom(createRoomDto);
        Optional<ChatRoom> chatRoom2 = chatRoomService.createRoom(createRoomDto);

        //then
        assertThat(chatRoom1, is(chatRoom2));
    }
}