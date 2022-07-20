package com.devit.chat.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Optional;
import java.util.UUID;

@Entity
@Getter @Setter
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    private UUID writerId;

    private String message;


    /* 연관관계 편의 메소드 */

    public void addMessage(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
        chatRoom.getMessages().add(this);
    }

    /* 생성 메서스 */

    public static Optional<Message> createMessage(UUID writerId, ChatRoom chatRoom, String message) {
        Message sampleMessage = new Message();
        sampleMessage.writerId = writerId;
        sampleMessage.addMessage(chatRoom);
        sampleMessage.message = message;

        return Optional.ofNullable(sampleMessage);
    }
}
