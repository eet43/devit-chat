package com.devit.chat.repository;

import com.devit.chat.entity.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    public List<ChatRoom> findByUserUUID(UUID uuid1, UUID uuid2) {
        return em.createQuery("select c from ChatRoom c where c.users = :uuid1 or c.users = :uuid2", ChatRoom.class)
                .setParameter("uuid1", uuid1)
                .setParameter("uuid2", uuid2)
                .getResultList();
    }

    public List<ChatRoom> findAllRooms(){
        List<ChatRoom> result = em.createQuery("select c from ChatRoom c order by c.modifiedAt DESC", ChatRoom.class)
                .getResultList();
        //채팅방 수정 순서 최근 순으로 반환

        return result;
    }


}
