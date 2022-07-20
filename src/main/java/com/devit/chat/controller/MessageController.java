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
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @MessageMapping(value = "/message")
    public void message(@RequestHeader("Authorization") String data , @RequestBody SendMessageDto messageDto){

        String[] chunks = data.split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(chunks[1]));

        JSONObject jsonObject = new JSONObject(payload);
        String sample = jsonObject.getString("uuid");
        UUID senderUuid = UUID.fromString(sample); //고쳐야함

        messageService.sendMessage(senderUuid, messageDto);
    }

//    @GetMapping("/messages/{roomId}")
//    public ResponseEntity<?> getRooms(@RequestHeader("Authorization") String data, @PathVariable("roomId") UUID roomId) {
//
//        String[] chunks = data.split("\\.");
//        Base64.Decoder decoder = Base64.getDecoder();
//        String payload = new String(decoder.decode(chunks[1]));
//
//        JSONObject jsonObject = new JSONObject(payload);
//        String sample = jsonObject.getString("uuid");
//        UUID uuid = UUID.fromString(sample);
//
//        Optional<ChatRoom> chatRoom = chatRoomService.enterRoom(new EnterRoomDto(roomId));
//
//        int httpStatus = HttpStatusChangeInt.ChangeStatusCode("OK");
//        String path = "/rooms";
//
//        ResponseDetails responseDetails = new ResponseDetails(new Date(), chatRoom, httpStatus, path);
//        return new ResponseEntity<>(responseDetails, HttpStatus.OK);
//    }
}
