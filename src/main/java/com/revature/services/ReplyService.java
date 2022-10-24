package com.revature.services;

import com.revature.dtos.UpdateReply;
import com.revature.entities.Account;
import com.revature.entities.Post;
import com.revature.entities.Reply;
import com.revature.repos.AccountRepository;
import com.revature.repos.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReplyService {
    @Autowired
    ReplyRepository replyRepository;
    @Autowired
    AccountRepository accountRepository;

    public Reply createReply(Reply reply){
        return this.replyRepository.save(reply);
    }

    public void updateReply(UpdateReply reply){
        Optional<Reply> oldReply = this.replyRepository.findById(reply.getReplyId());
        if(oldReply.isPresent()){
            oldReply.get().setContent(reply.getContent());
            oldReply.get().setEdited(true);
            this.replyRepository.save(oldReply.get());
        }else{
            throw new RuntimeException("Reply with given id not found");
        }
    }

    public void deleteReply(int replyId){
        Optional<Reply> oldReply = this.replyRepository.findById(replyId);
        if(oldReply.isPresent()){
            oldReply.get().setContent("[deleted]");
            oldReply.get().getAuthor().setAccountId(-1);
            this.replyRepository.save(oldReply.get());
        }else{
            throw new RuntimeException("Reply with given id not found");
        }
    }

    public void nodootReply(String username, int replyId){
        Optional<Reply> reply = this.replyRepository.findById(replyId);
        Optional<Account> account = this.accountRepository.findByUsername(username);
        if(reply.isPresent() && account.isPresent()){
            reply.get().getLikes().remove(account.get());
            reply.get().getDislikes().remove(account.get());
            this.replyRepository.save(reply.get());
        }else{
            throw new RuntimeException("Invalid reply id or username given");
        }
    }

    public void updootReply(String username, int replyId){
        Optional<Reply> reply = this.replyRepository.findById(replyId);
        Optional<Account> account = this.accountRepository.findByUsername(username);
        if(reply.isPresent() && account.isPresent()){
            reply.get().getDislikes().remove(account.get());
            reply.get().getLikes().add(account.get());
            this.replyRepository.save(reply.get());
        }else{
            throw new RuntimeException("Invalid reply id or username given");
        }
    }

    public void downdootReply(String username, int replyId){
        Optional<Reply> reply = this.replyRepository.findById(replyId);
        Optional<Account> account = this.accountRepository.findByUsername(username);
        if(reply.isPresent() && account.isPresent()){
            reply.get().getLikes().remove(account.get());
            reply.get().getDislikes().add(account.get());
            this.replyRepository.save(reply.get());
        }else{
            throw new RuntimeException("Invalid reply id or username given");
        }
    }
}
