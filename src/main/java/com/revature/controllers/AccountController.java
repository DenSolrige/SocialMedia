package com.revature.controllers;

import com.revature.dtos.PostInfo;
import com.revature.dtos.ReplyInfo;
import com.revature.entities.Account;
import com.revature.entities.Post;
import com.revature.entities.Reply;
import com.revature.services.AccountService;
import com.revature.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AccountController {
    @Autowired
    AccountService accountService;

    @Autowired
    LoginService loginService;

    @GetMapping("/{username}/post")
    public ResponseEntity<List<PostInfo>> getAccountPosts(@RequestHeader(required = false,name = "user") String jwt,@PathVariable("username") String username){
        Optional<Account> requestingUser = this.loginService.getRequestingUser(jwt);
        List<Post> posts = this.accountService.getAccountPosts(username);
        List<PostInfo> postInfoList = requestingUser.map(account -> posts.stream().map(post -> new PostInfo(post, account)).collect(Collectors.toList())).orElseGet(() -> posts.stream().map(PostInfo::new).collect(Collectors.toList()));
        return ResponseEntity.ok().body(postInfoList);
    }

    @GetMapping("/{username}/reply")
    public ResponseEntity<List<ReplyInfo>> getAccountReplies(@RequestHeader(required = false,name = "user") String jwt,@PathVariable("username") String username){
        Optional<Account> requestingUser = this.loginService.getRequestingUser(jwt);
        List<Reply> replies = this.accountService.getAccountReplies(username);
        List<ReplyInfo> replyInfoList = requestingUser.map(account -> replies.stream().map(reply -> new ReplyInfo(reply, account)).collect(Collectors.toList())).orElseGet(() -> replies.stream().map(ReplyInfo::new).collect(Collectors.toList()));
        return ResponseEntity.ok().body(replyInfoList);
    }
}
