package com.devit.chat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    @JsonIgnore
    private ChatRoom chatRoom;

    private String senderName;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID senderId;


    private String message;

    private LocalDateTime createdAt;


    /* 연관관계 편의 메소드 */

    public void addMessage(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
        chatRoom.getMessages().add(this);
    }

    /* 생성 메서스 */

    public static Optional<Message> createMessage(UUID senderId, String senderName, ChatRoom chatRoom, String message) {
        Message sampleMessage = new Message();
        sampleMessage.senderName = senderName;
        sampleMessage.senderId = senderId;
        sampleMessage.addMessage(chatRoom);
        sampleMessage.message = message;
        sampleMessage.setCreatedAt(LocalDateTime.now());

        return Optional.ofNullable(sampleMessage);
    }
}
