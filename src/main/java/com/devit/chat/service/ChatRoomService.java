package com.devit.chat.service;

import com.devit.chat.dto.CreateRoomDto;
import com.devit.chat.entity.ChatRoom;
import com.devit.chat.entity.RoomMember;
import com.devit.chat.repository.ChatRoomRepository;
import com.devit.chat.repository.RoomMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final RoomMemberRepository roomMemberRepository;

    public UUID save(CreateRoomDto createRoomDto) {
        //기존 방이 있으면
        UUID uuid = check(createRoomDto.getSenderId(), createRoomDto.getReceiverId());
        if(uuid != null) {
            return uuid;
        }

        //기존 방이 없으면
        ChatRoom chatRoom = ChatRoom.createChatRoom();
        UUID RoomUUID = chatRoomRepository.save(chatRoom);

        //리펙토링 해야할듯 동적이 아니라 너무 정적이다.
        //2명임을 제한하고 싶지 않아서 작성한 코드인데, 이러면 의미가 없다 ,,
        RoomMember join1 = RoomMember.createJoin(createRoomDto.getSenderId(), chatRoom);
        RoomMember join2 = RoomMember.createJoin(createRoomDto.getReceiverId(), chatRoom);
        Long u1 = roomMemberRepository.save(join1);
        Long u2 = roomMemberRepository.save(join2);

        return RoomUUID;

    }

    private UUID check(UUID senderId, UUID receiverId) {
        List<RoomMember> findRooms = roomMemberRepository.findByUserId(senderId, receiverId);
        List<ChatRoom> RoomList = findRooms.stream().map(RoomMember::getChatRoom).collect(Collectors.toList());

        for (ChatRoom chatRoom : RoomList) {
            if (RoomList.indexOf(chatRoom) != RoomList.lastIndexOf(chatRoom)) {
                return chatRoom.getRoomId();
            }
        }
        return null;
    }

    public ChatRoom findById(UUID uuid) {
        return chatRoomRepository.findByUUID(uuid);
    }

    public List<ChatRoom> findAll() {
        return chatRoomRepository.findAllRooms();
    }

}
