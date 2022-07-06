package com.devit.chat.entity;

import com.devit.chat.service.MessageService;
import com.devit.chat.util.Timestamped;
import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, columnDefinition = "BINARY(16)", name = "chatRoom_id")
    private UUID roomId;
    private String name;

    /* 생성 메서드 */
    public static ChatRoom createChatRoom(String name) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID();
        chatRoom.name = name;

        return chatRoom;
    }
}
