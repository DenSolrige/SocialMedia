package com.revature.controllers;

import com.revature.dtos.*;
import com.revature.entities.Account;
import com.revature.entities.Post;
import com.revature.entities.Reply;
import com.revature.services.LoginService;
import com.revature.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    LoginService loginService;

    @GetMapping("/{postId}/replies")
    public ResponseEntity<List<ReplyInfo>> getPostReplies(@RequestHeader(required = false,name = "user") String jwt,@PathVariable("postId") int postId){
        Optional<Account> requestingUser = this.loginService.getRequestingUser(jwt);
        List<Reply> replies = this.postService.getPostReplies(postId);
        List<ReplyInfo> replyInfoList = requestingUser.map(
                account -> replies
                        .stream()
                        .map(reply -> new ReplyInfo(reply, account))
                        .collect(Collectors.toList()))
                .orElseGet(() -> replies
                        .stream()
                        .map(ReplyInfo::new)
                        .collect(Collectors.toList()));
        return ResponseEntity.ok().body(replyInfoList);
    }

    @GetMapping
    public ResponseEntity<List<PostInfo>> getPosts(@RequestHeader(required = false,name = "user") String jwt){
        Optional<Account> requestingUser = this.loginService.getRequestingUser(jwt);
        List<Post> posts = this.postService.getPosts();
        List<PostInfo> postInfoList = requestingUser.map(
                account -> posts
                        .stream()
                        .map(post -> new PostInfo(post, account))
                        .collect(Collectors.toList()))
                .orElseGet(() -> posts
                        .stream()
                        .map(PostInfo::new)
                        .collect(Collectors.toList()));
        return ResponseEntity.ok().body(postInfoList);
    }

    @PostMapping
    public ResponseEntity<PostInfo> createPost(@RequestHeader(name = "user") String jwt, @RequestBody CreatePost createPost){
        Optional<Account> requestingUser = this.loginService.getRequestingUser(jwt);
        return requestingUser.map(
                account -> ResponseEntity
                        .ok()
                        .body(
                                new PostInfo(
                                        this.postService.createPost(
                                                new Post(
                                                        createPost.getTitle(),
                                                        createPost.getContent(),
                                                        account,
                                                        System.currentTimeMillis() / 1000)))))
                .orElseGet(() -> ResponseEntity.unprocessableEntity().body(null));
    }

    @PostMapping("/{postId}/replies")
    public ResponseEntity<ReplyInfo> createReply(@RequestHeader(name = "user") String jwt, @RequestBody CreateReply createReply,@PathVariable("postId") int postId){
        Optional<Account> requestingUser = this.loginService.getRequestingUser(jwt);
        return requestingUser.map(
                account -> ResponseEntity
                        .ok()
                        .body(
                                new ReplyInfo(
                                        this.postService.createReply(account, postId, createReply)
                                )))
                .orElseGet(
                        () -> ResponseEntity.unprocessableEntity().body(null)
                );
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<PostInfo> editPost(@RequestHeader(name = "user") String jwt, @PathVariable("postId") int postId, @RequestBody CreatePost updatePost){
        Optional<Account> requestingUser = this.loginService.getRequestingUser(jwt);
        if(requestingUser.isPresent() && requestingUser.get().getPosts().stream().anyMatch(post -> post.getPostId()==postId)){
            return ResponseEntity
                    .ok()
                    .body(new PostInfo(this.postService.updatePost(postId,updatePost)));
        }else{
            return ResponseEntity.unprocessableEntity().body(null);
        }
    }

    @DeleteMapping("/{postId}")
    public HttpStatus deletePost(@RequestHeader(name = "user") String jwt, @PathVariable("postId") int postId){
        Optional<Account> requestingUser = this.loginService.getRequestingUser(jwt);
        if(requestingUser.isPresent() && requestingUser.get().getPosts().stream().anyMatch(post -> post.getPostId()==postId)){
            this.postService.deletePostById(postId);
            return HttpStatus.OK;
        }else{
            return HttpStatus.BAD_REQUEST;
        }
    }

    @PutMapping("/{postId}/doot")
    public HttpStatus dootPost(@RequestHeader(name = "user") String jwt, @PathVariable("postId") int postId, @RequestBody DootStatus dootStatus){
        Optional<Account> requestingUser = this.loginService.getRequestingUser(jwt);
        if(requestingUser.isPresent()){
            this.postService.dootPost(requestingUser.get().getUsername(),postId,dootStatus);
            return HttpStatus.OK;
        }else{
            return HttpStatus.BAD_REQUEST;
        }
    }
}
