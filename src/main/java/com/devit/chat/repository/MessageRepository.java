package com.devit.chat.repository;

import com.devit.chat.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class MessageRepository {
    private final EntityManager em;

    public void save(Message message) {
        em.persist(message);
    }

    public Optional<List<Message>> loadMessages(UUID roomId) {
        List<Message> messages = em.createQuery("select m from Message m join m.chatRoom c where c.roomId = :roomId", Message.class)
                .setFirstResult(1)
                .setMaxResults(10)
                .setParameter("roomId", roomId)
                .getResultList();

        return Optional.ofNullable(messages);
    }


}
