//package com.devit.chat.controller;
//
//import com.devit.chat.dto.CreateRoomDto;
//import com.devit.chat.entity.ChatRoom;
//import com.devit.chat.entity.Message;
//import com.devit.chat.repository.ChatRoomRepository;
//import com.devit.chat.service.ChatRoomService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequiredArgsConstructor
//@Log4j2
//public class ChatRoomController {
//    private final ChatRoomService chatRoomService;
//
//
//    //채팅방 목록 조회
//
//    //채팅방 개설 (RedirectAttributes rttr)
//    @PostMapping(value = "/room")
//    public ChatRoom create(@RequestBody CreateRoomDto createRoomDto){
//
//        log.info("# Create Chat Room ");
//        ChatRoom chatRoom = chatRoomService.save(createRoomDto);
////        return "redirect:api/chat/rooms";
//
//        return chatRoom;
//    }
//
//
//    //채팅방 조회
//    @GetMapping("room/{roomId}")
//    public ChatRoom getRoom(@PathVariable("roomId") String roomId){
//
//        ChatRoom chatRoom = chatRoomService.findById(roomId);
//        return chatRoom;
//    }
//}
