package com.devit.chat.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter

public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatRoom_id")
    private ChatRoom chatRoom;

    @Column(unique = true)
    private UUID writerId;

    private String message;
}
