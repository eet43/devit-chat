package com.devit.chat.controller;

import com.devit.chat.dto.CreateRoomDto;
import com.devit.chat.dto.EnterRoomDto;
import com.devit.chat.dto.response.ResponseDetails;
import com.devit.chat.entity.ChatRoom;
import com.devit.chat.service.ChatRoomService;
import com.devit.chat.util.HttpStatusChangeInt;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 1. 채팅방 생성
 * 2. 채팅목록 채팅방 입장
 * 3. 채팅방 목록 조회
 */

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatRoomController {
    private final ChatRoomService chatRoomService;


    @PostMapping(value = "/rooms") //이게 진짜임
    public ResponseEntity<?> create(@RequestHeader("Authorization") String data, @RequestBody CreateRoomDto createRoomDto) {


        String[] chunks = data.split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(chunks[1]));

        JSONObject jsonObject = new JSONObject(payload);
        String sample = jsonObject.getString("uuid");
        UUID uuid = UUID.fromString(sample);

        log.info("ChatRoom : {} 해당 유저들의 채팅방을 생성합니다.", createRoomDto);

        Optional<ChatRoom> chatRoom = chatRoomService.createRoom(uuid, createRoomDto);

        log.info("ChatRoom : {} 해당 유저들의 채팅방을 생성 완료 후 반환합니다..", chatRoom);

        int httpStatus = HttpStatusChangeInt.ChangeStatusCode("Created");
        String path = "/rooms";

        ResponseDetails responseDetails = new ResponseDetails(new Date(), chatRoom, httpStatus, path);
        return new ResponseEntity<>(responseDetails, HttpStatus.CREATED);
    }

    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<?> getRooms(@RequestHeader("Authorization") String data, @PathVariable("roomId") UUID roomId) {

        String[] chunks = data.split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(chunks[1]));

        JSONObject jsonObject = new JSONObject(payload);
        String sample = jsonObject.getString("uuid");
        UUID uuid = UUID.fromString(sample);

        Optional<ChatRoom> chatRoom = chatRoomService.enterRoom(new EnterRoomDto(roomId));

        log.info("ChatRoom : {} 해당 채팅방을 입장합니다.", chatRoom);


        int httpStatus = HttpStatusChangeInt.ChangeStatusCode("OK");
        String path = "/rooms";

        ResponseDetails responseDetails = new ResponseDetails(new Date(), chatRoom, httpStatus, path);
        return new ResponseEntity<>(responseDetails, HttpStatus.OK);
    }



    @GetMapping("/rooms")
    public ResponseEntity<?> getRooms(@RequestHeader("Authorization") String data) {

        String[] chunks = data.split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(chunks[1]));

        JSONObject jsonObject = new JSONObject(payload);
        String sample = jsonObject.getString("uuid");
        UUID uuid = UUID.fromString(sample);


        Optional<List<ChatRoom>> chatRooms = chatRoomService.findAllRoom(uuid);
        log.info("ChatRoomList : {} 채팅방의 목록을 반환합니다.", chatRooms);

        int httpStatus = HttpStatusChangeInt.ChangeStatusCode("OK");
        String path = "/rooms";

        ResponseDetails responseDetails = new ResponseDetails(new Date(), chatRooms, httpStatus, path);
        return new ResponseEntity<>(responseDetails, HttpStatus.OK);
    }
}
