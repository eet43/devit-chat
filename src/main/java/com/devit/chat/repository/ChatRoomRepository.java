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

    public ChatRoom save(ChatRoom chatRoom) {
        em.persist(chatRoom);
        return chatRoom;
    }

    public ChatRoom findByUUID(String uuid) {
        return em.createQuery("select c from ChatRoom c where c.roomId = :uuid", ChatRoom.class)
                .setParameter("uuid", uuid)
                .getSingleResult();
    }


    public List<ChatRoom> findByUserUUID(String uuid1, String uuid2) {
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
