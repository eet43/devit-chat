package com.devit.chat.controller;

import com.devit.chat.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final SimpMessagingTemplate template;


    //Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
    @MessageMapping(value = "/api/chat/enter")
    @SendTo("/sub/")
    public void enter(Message message){
        message.setMessage(message.getSender() + "님이 채팅방에 참여하였습니다.");
        template.convertAndSend("/sub/api/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "/api/chat/message")
    public void message(Message message){
        template.convertAndSend("/sub/api/chat/room/" + message.getRoomId(), message);
    }
}
