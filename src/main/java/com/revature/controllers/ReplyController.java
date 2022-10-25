package com.revature.controllers;

import com.revature.dtos.*;
import com.revature.entities.Account;
import com.revature.services.LoginService;
import com.revature.services.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/replies")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReplyController {

    @Autowired
    ReplyService replyService;

    @Autowired
    LoginService loginService;

    @PatchMapping("/{replyId}")
    public ResponseEntity<ReplyInfo> editReply(@RequestHeader(name = "user") String jwt, @PathVariable("replyId") int replyId,CreateReply updateReply){
        Optional<Account> requestingUser = this.loginService.getRequestingUser(jwt);
        if(requestingUser.isPresent() && requestingUser.get().getReply().stream().anyMatch(reply -> reply.getReplyId()==replyId)){
            return ResponseEntity.ok().body(new ReplyInfo(this.replyService.updateReply(replyId,updateReply)));
        }else{
            return ResponseEntity.unprocessableEntity().body(null);
        }
    }

    @DeleteMapping("/{replyId}")
    public HttpStatus deleteReply(@RequestHeader(name = "user") String jwt, @PathVariable("replyId") int replyId){
        Optional<Account> requestingUser = this.loginService.getRequestingUser(jwt);
        if(requestingUser.isPresent() && requestingUser.get().getReply().stream().anyMatch(reply -> reply.getReplyId()==replyId)){
            this.replyService.deleteReply(replyId);
            return HttpStatus.OK;
        }else{
            return HttpStatus.BAD_REQUEST;
        }
    }

    @PutMapping("/{replyId}/doot")
    public HttpStatus dootPost(@RequestHeader(name = "user") String jwt, @PathVariable("replyId") int replyId, @RequestBody DootStatus dootStatus){
        Optional<Account> requestingUser = this.loginService.getRequestingUser(jwt);
        if(requestingUser.isPresent()){
            this.replyService.dootReply(requestingUser.get().getUsername(),replyId,dootStatus);
            return HttpStatus.OK;
        }else{
            return HttpStatus.BAD_REQUEST;
        }
    }
}
