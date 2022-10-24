package com.revature.controllers;

import com.revature.dtos.ReplyInfo;
import com.revature.entities.Account;
import com.revature.entities.Reply;
import com.revature.services.LoginService;
import com.revature.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/post")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    LoginService loginService;

    @GetMapping("/{postId}/reply")
    public ResponseEntity<List<ReplyInfo>> getPostReplies(@RequestHeader(required = false,name = "user") String jwt,@PathVariable("postId") int postId){
        Optional<Account> requestingUser = this.loginService.getRequestingUser(jwt);
        List<Reply> replies = this.postService.getPostReplies(postId);
        List<ReplyInfo> replyInfoList = requestingUser.map(account -> replies.stream().map(reply -> new ReplyInfo(reply, account)).collect(Collectors.toList())).orElseGet(() -> replies.stream().map(ReplyInfo::new).collect(Collectors.toList()));
        return ResponseEntity.ok().body(replyInfoList);
    }
}
