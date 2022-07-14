package com.devit.chat.controller;

import com.devit.chat.dto.CreateRoomDto;
import com.devit.chat.dto.EnterRoomDto;
import com.devit.chat.dto.response.ResponseDetails;
import com.devit.chat.entity.ChatRoom;
import com.devit.chat.entity.Message;
import com.devit.chat.exception.NoResourceException;
import com.devit.chat.repository.ChatRoomRepository;
import com.devit.chat.service.ChatRoomService;
import com.devit.chat.util.HttpStatusChangeInt;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

/**
 * 1. 채팅방 생성
 * 2. 채팅목록 채팅방 입장
 * 3. 채팅방 목록 조회
 */

@RestController
@RequiredArgsConstructor
@Log4j2
public class ChatRoomController {
    private final ChatRoomService chatRoomService;


    @PostMapping(value = "/rooms")
    public ResponseEntity<?> create(@RequestHeader("Authorization") String data, @RequestBody CreateRoomDto createRoomDto) {

        log.info("채팅방 생성 ");

        String[] chunks = data.split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(chunks[1]));

        JSONObject jsonObject = new JSONObject(payload);
        String sample = jsonObject.getString("uuid");
        UUID uuid = UUID.fromString(sample);

        Optional<ChatRoom> chatRoom = chatRoomService.createRoom(uuid, createRoomDto);

        int httpStatus = HttpStatusChangeInt.ChangeStatusCode("Created");
        String path = "/rooms";

        ResponseDetails responseDetails = new ResponseDetails(new Date(), chatRoom, httpStatus, path);
        return new ResponseEntity<>(responseDetails, HttpStatus.CREATED);
    }

    @PostMapping("/rooms/{roomId}")
    public ResponseEntity<?> getRooms(@RequestHeader("Authorization") String data, @PathVariable("roomId") UUID roomId) {

        String[] chunks = data.split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(chunks[1]));

        JSONObject jsonObject = new JSONObject(payload);
        String sample = jsonObject.getString("uuid");
        UUID uuid = UUID.fromString(sample);

        Optional<ChatRoom> chatRoom = chatRoomService.enterRoom(new EnterRoomDto(roomId));

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

        int httpStatus = HttpStatusChangeInt.ChangeStatusCode("OK");
        String path = "/rooms";

        ResponseDetails responseDetails = new ResponseDetails(new Date(), chatRooms, httpStatus, path);
        return new ResponseEntity<>(responseDetails, HttpStatus.OK);
    }
}
