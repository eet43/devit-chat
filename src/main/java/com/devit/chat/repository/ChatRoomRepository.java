package com.devit.chat.repository;

import com.devit.chat.entity.ChatRoom;
import com.devit.chat.entity.Message;
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

    public Optional<ChatRoom> findByUUID(UUID uuid) {
        ChatRoom findRoom = em.createQuery("select c from ChatRoom c where c.roomId = :uuid", ChatRoom.class)
                .setParameter("uuid", uuid)
                .getSingleResult();

        return Optional.ofNullable(findRoom);
    }


    public Optional<ChatRoom> findByUser(UUID senderId, UUID receiverId) {
        List<ChatRoom> list = em.createQuery("select c from ChatRoom c where (c.senderId = :senderId and c.receiverId = :receiverId) " +
                        "or (c.receiverId = :senderId and c.senderId = :receiverId)", ChatRoom.class)
                .setParameter("senderId", senderId)
                .setParameter("receiverId", receiverId)
                .getResultList();

        return list.stream().findAny();
    }

    public Optional<List<ChatRoom>> findAllRooms(UUID userId){
        List<ChatRoom> result = em.createQuery("select c from ChatRoom c " +
                        "where c.senderId = :userId or c.receiverId = :userId", ChatRoom.class)
                .setParameter("userId", userId)
                .getResultList();
        //채팅방 수정 순서 최근 순으로 반환

        return Optional.ofNullable(result);
    }

    public Optional<List<Message>> findMessageByUUID(UUID chatId) {
        List<Message> messages = em.createQuery("select c.message from ChatRoom c where c.roomId = :uuid order by m.createdAt", Message.class)
                .setFirstResult(1)
                .setMaxResults(10)
                .setParameter("uuid", chatId)
                .getResultList();

        return Optional.ofNullable(messages);
    }




}
