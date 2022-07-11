package com.devit.chat.entity;

import com.devit.chat.util.Timestamped;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    @Column(nullable = false, unique = true, columnDefinition = "BINARY(16)", name = "chat_room_id")
//    private UUID roomId;

    private String roomName;
    private String roomId;

    @JsonIgnore
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<RoomMember> roomMembers = new ArrayList<>();


   /* 생성 메서드 */
    public static ChatRoom createChatRoom(String roomName) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.roomName = roomName;
        return chatRoom;
    }
}
