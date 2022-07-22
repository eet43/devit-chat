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

    @Column(name="room_uuid", unique = true, columnDefinition = "BINARY(16)")
    private UUID roomId;

    private String senderName;

    private String receiverName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_room_id")
    private List<Message> messages = new ArrayList<>();

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID boardId;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID senderId;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID receiverId;


   /* 생성 메서드 */
    public static ChatRoom createChatRoom(String senderName, String receiverName, UUID boardId, UUID senderId, UUID receiverId) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.senderName = senderName;
        chatRoom.receiverName = receiverName;
        chatRoom.roomId = UUID.randomUUID();
        chatRoom.boardId = boardId;
        chatRoom.senderId = senderId;
        chatRoom.receiverId = receiverId;
        return chatRoom;
    }
}
