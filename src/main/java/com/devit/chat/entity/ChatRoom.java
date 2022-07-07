package com.devit.chat.entity;

import com.devit.chat.util.Timestamped;
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

    @Column(nullable = false, unique = true, columnDefinition = "BINARY(16)", name = "chatRoom_id")
    private UUID roomId;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();

    @Column(name = "user_id")
    @ElementCollection(fetch = FetchType.LAZY)
    private List<UUID> users = new ArrayList<>(); // 참가유저


   /* 생성 메서드 */
    public static ChatRoom createChatRoom(UUID senderID, UUID receiverID) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID();
        chatRoom.users.add(senderID);
        chatRoom.users.add(receiverID);
        return chatRoom;
    }
}
