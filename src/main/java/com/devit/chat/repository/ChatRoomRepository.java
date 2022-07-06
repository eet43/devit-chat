package com.devit.chat.repository;

import com.devit.chat.entity.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepository {
    private final EntityManager em;

    public UUID save(ChatRoom chatRoom) {
        em.persist(chatRoom);
        return chatRoom.getRoomId();
    }

    public ChatRoom findByUUID(UUID uuid) {
        return em.createQuery("select c from ChatRoom c where c.roomId = :uuid", ChatRoom.class)
                .setParameter("uuid", uuid)
                .getSingleResult();
    }

    public ChatRoom findByName(String name) {
        return em.createQuery("select c from ChatRoom c where c.name = :name", ChatRoom.class)
                .setParameter("name", name)
                .getSingleResult();
    }


}
