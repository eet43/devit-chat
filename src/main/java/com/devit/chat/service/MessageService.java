package com.devit.chat.service;

import com.devit.chat.dto.SendMessageDto;
import com.devit.chat.entity.ChatRoom;
import com.devit.chat.entity.Message;
import com.devit.chat.repository.ChatRoomRepository;
import com.devit.chat.repository.MessageRepository;
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
    private final MessageRepository messageRepository;
    private final SimpMessageSendingOperations sendingOperations;


    public String sendMessage(SendMessageDto messageDto) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findByUUID(messageDto.getRoomId());

        Optional<Message> message = Message.createMessage(messageDto.getUserId(), messageDto.getSenderName(), chatRoom.get(), messageDto.getMessage());
        messageRepository.save(message.get());

        log.info("메시지 시작");

        try {
            sendingOperations.convertAndSend("/sub/rooms/" + messageDto.getRoomId(), message);
            return "메시지 전송 완료.";

        } catch (Exception e) {
            return "메시지 전송 실패 : " + e.getMessage();
        }

    }

    public Optional<List<Message>> loadMessage(UUID roomId) {

//        return chatRoomRepository.findMessageByUUID(roomId);
        return messageRepository.loadMessages(roomId);
    }
}
