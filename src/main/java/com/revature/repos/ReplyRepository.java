package com.revature.repos;

import com.revature.entities.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply,Integer> {
    List<Reply> findByPostId(int id);
}
