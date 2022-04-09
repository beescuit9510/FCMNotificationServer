package com.example.batchproject.model.repository;

import com.example.batchproject.model.vo.PushMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface messageRepository extends JpaRepository<PushMessage, Long> {


    @Modifying
    @Query(value = "SELECT * FROM push_message p WHERE p.is_sent = 0 ",nativeQuery = true)
    List<PushMessage> selectMessageToSend();

    @Modifying
    @Query(value = "UPDATE push_message set is_sent = true where is_sent = 0 and p.is_sent = ?",nativeQuery = true)
    List<PushMessage> updateIsSentTrue(Long pushMessageNo);

}


