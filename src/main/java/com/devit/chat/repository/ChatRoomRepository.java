package com.devit.chat.repository;

import com.devit.chat.entity.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepository {
    private final EntityManager em;

    public ChatRoom save(ChatRoom chatRoom) {
        em.persist(chatRoom);
        return chatRoom;
    }

    public ChatRoom findByUUID(UUID uuid) {
        return em.createQuery("select c from ChatRoom c where c.roomId = :uuid", ChatRoom.class)
                .setParameter("uuid", uuid)
                .getSingleResult();
    }


    public Optional<ChatRoom> findByUser(UUID senderId, UUID receiverId) {
        List<ChatRoom> list = em.createQuery("select c from ChatRoom c where (c.senderId = :senderId and c.receiverId = :receiverId) " +
                        "or (c.receiverId = :senderId and c.senderId = :receiverId)", ChatRoom.class)
                .setParameter("senderId", senderId)
                .setParameter("receiverId", receiverId)
                .getResultList();

        return list.stream().findAny();
    }

    public List<ChatRoom> findAllRooms(UUID userId){
        List<ChatRoom> result = em.createQuery("select c.roomId, c.roomName from ChatRoom c " +
                        "where c.senderId = :userId or c.receiverId = :userId", ChatRoom.class)
                .setParameter("userId", userId)
                .getResultList();
        //채팅방 수정 순서 최근 순으로 반환

        return result;
    }




}
