package com.devit.chat.service;

import com.devit.chat.dto.CreateRoomDto;
import com.devit.chat.entity.ChatRoom;
import com.devit.chat.entity.RoomMember;
import com.devit.chat.repository.ChatRoomRepository;
import com.devit.chat.repository.RoomMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.server.UID;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final RoomMemberRepository roomMemberRepository;

    public ChatRoom save(CreateRoomDto createRoomDto) {
        log.info("{}", createRoomDto);
        String senderId = createRoomDto.getSenderId();
        String receiverId = createRoomDto.getReceiverId();

        log.info("receiverId : ", receiverId);
        //기존 방이 있으면
        String uuid = check(senderId, receiverId);
        if(uuid != null) {
            return chatRoomRepository.findByUUID(uuid);
        }

        //기존 방이 없으면
        ChatRoom chatRoom = chatRoomRepository.save(ChatRoom.createChatRoom(createRoomDto.getRoomName()));

        //리펙토링 해야할듯 동적이 아니라 너무 정적이다.
        //2명임을 제한하고 싶지 않아서 작성한 코드인데, 이러면 의미가 없다 ,,
        RoomMember join1 = RoomMember.createJoin(senderId, chatRoom);
        RoomMember join2 = RoomMember.createJoin(receiverId, chatRoom);
        Long u1 = roomMemberRepository.save(join1);
        Long u2 = roomMemberRepository.save(join2);

        return chatRoom;

    }

    private String check(String senderId, String receiverId) {
        log.info("매개변수 : ", senderId);
        List<RoomMember> findRooms = roomMemberRepository.findByUserId(senderId, receiverId);
        if(findRooms == null) {
            return null;
        }
        log.info("{}", findRooms);
        List<ChatRoom> RoomList = findRooms.stream().map(RoomMember::getChatRoom).collect(Collectors.toList());

        log.info("{}", RoomList);

        for (ChatRoom chatRoom : RoomList) {
            if (RoomList.indexOf(chatRoom) != RoomList.lastIndexOf(chatRoom)) {
                return chatRoom.getRoomId();
            }
        }
        return null;
    }

    public ChatRoom findById(String uuid) {
        return chatRoomRepository.findByUUID(uuid);
    }

    public List<ChatRoom> findAll() {
        return chatRoomRepository.findAllRooms();
    }

}
