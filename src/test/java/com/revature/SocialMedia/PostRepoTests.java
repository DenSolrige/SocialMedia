package com.revature.SocialMedia;

import com.revature.entities.Account;
import com.revature.entities.Post;
import com.revature.entities.Reply;
import com.revature.repos.AccountRepository;
import com.revature.repos.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@SpringBootTest
//@Transactional
public class PostRepoTests {
    @Autowired
    PostRepository postRepository;

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void new_account_making_post(){
        Account account = new Account("testuser","testpassword");
        this.accountRepository.save(account);
        Post post = new Post("testTitle","testContent",account, System.currentTimeMillis()/1000);
        this.postRepository.save(post);
        Optional<Account> fetchedAccount = this.accountRepository.findById(account.getAccountId());
        Assertions.assertNotEquals(0,post.getPostId());
        Assertions.assertTrue(fetchedAccount.isPresent());
        Assertions.assertEquals(1,fetchedAccount.get().getPosts().size());
        System.out.println(fetchedAccount.get().getPosts());
    }
}
