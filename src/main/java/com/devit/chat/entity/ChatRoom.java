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

    @Column(nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID roomId;
    private String roomName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    private List<Message> messages = new ArrayList<>();

    @Column(nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID senderId;

    @Column(nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID receiverId;


   /* 생성 메서드 */
    public static ChatRoom createChatRoom(String roomName, UUID senderId, UUID receiverId) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID();
        chatRoom.senderId = senderId;
        chatRoom.receiverId = receiverId;
        chatRoom.roomName = roomName;
        return chatRoom;
    }
}
