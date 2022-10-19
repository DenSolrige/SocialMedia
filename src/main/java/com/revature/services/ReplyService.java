package com.revature.services;

import com.revature.entities.Reply;
import com.revature.repos.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService {
    @Autowired
    ReplyRepository replyRepository;

    public List<Reply> getPostReplies(int postId){
        return this.replyRepository.findByPostId(postId);
    }
}
