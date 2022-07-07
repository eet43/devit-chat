package com.devit.chat.controller;

import com.devit.chat.dto.CreateRoomDto;
import com.devit.chat.entity.ChatRoom;
import com.devit.chat.repository.ChatRoomRepository;
import com.devit.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Log4j2
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    //채팅방 목록 조회
    @GetMapping(value = "/api/chat/rooms")
    public ModelAndView rooms(){

        log.info("# All Chat Rooms");
        ModelAndView mv = new ModelAndView("chat/rooms");

        mv.addObject("list", chatRoomService.findAll());

        return mv;
    }

    //채팅방 개설 (RedirectAttributes rttr)
    @PostMapping(value = "/api/chat/room")
    public String create(@RequestBody CreateRoomDto createRoomDto){

        log.info("# Create Chat Room ");
        UUID chatRoomId = chatRoomService.save(createRoomDto);
//        rttr.addFlashAttribute("roomId", chatRoomId);
        return "redirect:api/chat/rooms";
    }

    //채팅방 조회
    @GetMapping("api/room")
    public void getRoom(UUID roomId, Model model){

        log.info("# get Chat Room, roomID : " + roomId);

        model.addAttribute("room", chatRoomService.findById(roomId));
    }
}
