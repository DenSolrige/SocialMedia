package com.revature.services;

import com.revature.entities.Account;
import com.revature.entities.Post;
import com.revature.entities.Reply;
import com.revature.repos.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    public List<Post> getAccountPosts(String username){
        Optional<Account> account = this.accountRepository.findByUsername(username);
        if(account.isPresent()){
            return account.get().getPosts();
        }else{
            throw new RuntimeException("No account found with given username");
        }
    }

    public List<Reply> getAccountReplies(String username){
        Optional<Account> account = this.accountRepository.findByUsername(username);
        if(account.isPresent()){
            return account.get().getReply();
        }else{
            throw new RuntimeException("No account found with given username");
        }
    }
}
