package com.devit.chat.repository;

import com.devit.chat.entity.ChatRoom;
import com.devit.chat.entity.RoomMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

/**
 * Optional 로 통한,Null 예외 처리 필요함
 */
@Repository
@RequiredArgsConstructor
public class RoomMemberRepository {
    private final EntityManager em;

    public Long save(RoomMember roomMember) {
        em.persist(roomMember);
        return roomMember.getId();
    }

    public List<RoomMember> findByUserId(String userId1, String userId2) {
        return em.createQuery("select rm from RoomMember rm where rm.userId = :userId1 or rm.userId = :userId2", RoomMember.class)
                .setParameter("userId1", userId1)
                .setParameter("userId2", userId2)
                .getResultList();
    }
}
