package com.devit.chat.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomMember {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(nullable = false, columnDefinition = "BINARY(16)")
//    private UUID userId;

    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatRoom_id") //referenceCoulmnName = ""
    private ChatRoom chatRoom;

    public void addJoin(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
        chatRoom.getRoomMembers().add(this);
    }

    /* 생성메서드 */
    public static RoomMember createJoin(String userId, ChatRoom chatRoom) {
        RoomMember roomMember = new RoomMember();
        roomMember.userId = userId;
        roomMember.addJoin(chatRoom);
        return roomMember;
    }
}
