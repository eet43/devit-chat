package com.devit.chat.controller;

import com.devit.chat.entity.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MessageController {
    private final SimpMessageSendingOperations sendingOperations;

    @MessageMapping(value = "/enter")
    public void enter(Message message){
        log.info("입장시작", message);
        message.setMessage(message.getName() + "님이 채팅방에 참여하였습니다.");
        String address = message.getChatRoom().getRoomId();
        sendingOperations.convertAndSend("/sub/room/" + address, message);
    }


    @MessageMapping(value = "/message")
    public void message(Message message){
        log.info("메시지 시작");
        sendingOperations.convertAndSend("/sub/room/" + message.getChatRoom().getRoomId(), message);
    }
}
