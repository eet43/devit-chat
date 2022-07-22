package com.devit.chat.controller;

import com.devit.chat.dto.EnterRoomDto;
import com.devit.chat.dto.SendMessageDto;
import com.devit.chat.dto.response.ResponseDetails;
import com.devit.chat.entity.ChatRoom;
import com.devit.chat.entity.Message;
import com.devit.chat.service.MessageService;
import com.devit.chat.util.HttpStatusChangeInt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @MessageMapping(value = "/message")
    public void message(SendMessageDto messageDto){


        log.info("Message : {} 해당 메시지를 전달합니다.", messageDto);

        messageService.sendMessage(messageDto);
    }

    @GetMapping("/messages/{roomId}")
    public ResponseEntity<?> getRooms(@RequestHeader("Authorization") String data, @PathVariable("roomId") UUID roomId) {

        String[] chunks = data.split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(chunks[1]));

        JSONObject jsonObject = new JSONObject(payload);
        String sample = jsonObject.getString("uuid");
        UUID uuid = UUID.fromString(sample);

        Optional<List<Message>> messages = messageService.loadMessage(roomId);

        log.info("Message : {} 채팅방에 속한 메시지를 반환합니다.", messages);

        int httpStatus = HttpStatusChangeInt.ChangeStatusCode("OK");
        String path = "/rooms";

        ResponseDetails responseDetails = new ResponseDetails(new Date(), messages, httpStatus, path);
        return new ResponseEntity<>(responseDetails, HttpStatus.OK);
    }
}
