package com.devit.chat.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
public class RoomMember {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID userId;

    @ManyToOne
    @JoinColumn(name = "chatRoom_id")
    private ChatRoom chatRoom;
}
