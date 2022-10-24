package com.revature.SocialMedia;

import com.revature.entities.Account;
import com.revature.entities.Reply;
import com.revature.repos.ReplyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional
public class ReplyRepoTests {
    @Autowired
    ReplyRepository replyRepository;

//    @Test
//    public void get_reply_account_test(){
//        List<Reply> replies = this.replyRepository.findByPostId(1);
//        List<Account> accounts = replies.stream().map(Reply::getAccount).collect(Collectors.toList());
//        System.out.println(accounts);
//    }
}
