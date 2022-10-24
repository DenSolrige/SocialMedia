package com.revature.SocialMedia;

import com.revature.entities.Account;
import com.revature.repos.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class AccountRepoTests {
    @Autowired
    AccountRepository accountRepository;

    @Test
    public void create_account(){
        Account account = new Account("testuser","testpass");
        this.accountRepository.save(account);
        Assertions.assertNotEquals(0,account.getAccountId());
    }
}
