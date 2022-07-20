package com.devit.chat.service;

import com.devit.chat.dto.SendMessageDto;
import com.devit.chat.entity.ChatRoom;
import com.devit.chat.entity.Message;
import com.devit.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {

    private final ChatRoomRepository chatRoomRepository;
    private final SimpMessageSendingOperations sendingOperations;


    public String sendMessage(UUID senderId, SendMessageDto messageDto) {
        ChatRoom chatRoom = messageDto.getRoom();
        UUID roomId = chatRoom.getRoomId(); // 나중에 없애야함 테스트 용
        Optional<Message> message = Message.createMessage(senderId,chatRoom, messageDto.getMessage());


        log.info("메시지 시작");

        try {
            sendingOperations.convertAndSend("/sub/rooms/" + roomId, message);
            return "메시지 전송 완료.";

        } catch (Exception e) {
            return "메시지 전송 실패 : " + e.getMessage();
        }

    }

//    public List<Message> loadMessage(UUID roomId) {
//        return
//    }
}
