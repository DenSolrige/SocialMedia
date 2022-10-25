package com.revature.services;

import com.revature.dtos.CreateReply;
import com.revature.dtos.DootStatus;
import com.revature.entities.Account;
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

    public Reply updateReply(int replyId,CreateReply reply){
        Optional<Reply> oldReply = this.replyRepository.findById(replyId);
        if(oldReply.isPresent()){
            oldReply.get().setContent(reply.getContent());
            oldReply.get().setEdited(true);
            return this.replyRepository.save(oldReply.get());
        }else{
            throw new RuntimeException("Reply with given id not found");
        }
    }

    public void deleteReply(int replyId){
        Optional<Reply> oldReply = this.replyRepository.findById(replyId);
        if(oldReply.isPresent()){
            oldReply.get().setContent("[deleted]");
            Optional<Account> deletedPostAccount = this.accountRepository.findById(-1);
            oldReply.get().setAuthor(deletedPostAccount.get());
            this.replyRepository.save(oldReply.get());
        }else{
            throw new RuntimeException("Reply with given id not found");
        }
    }

    public void dootReply(String username, int replyId, DootStatus dootStatus){
        Optional<Reply> optionalReply = this.replyRepository.findById(replyId);
        Optional<Account> optionalAccount = this.accountRepository.findByUsername(username);
        if(optionalReply.isPresent() && optionalAccount.isPresent()){
            Reply reply = optionalReply.get();
            Account account = optionalAccount.get();

            if(dootStatus.equals(DootStatus.UPDOOT)){
                reply.getLikes().add(account);
                reply.getDislikes().remove(account);
                account.getLikedReplies().add(reply);
                account.getDislikedReplies().remove(reply);
            }else if(dootStatus.equals(DootStatus.DOWNDOOT)){
                reply.getLikes().remove(account);
                reply.getDislikes().add(account);
                account.getLikedReplies().remove(reply);
                account.getDislikedReplies().add(reply);
            }else{
                reply.getLikes().remove(account);
                reply.getDislikes().remove(account);
                account.getLikedReplies().remove(reply);
                account.getDislikedReplies().remove(reply);
            }

            this.replyRepository.save(reply);
            this.accountRepository.save(account);
        }else{
            throw new RuntimeException("Invalid reply id or username given");
        }
    }
}
