package com.revature.controllers;

import com.revature.entities.Reply;
import com.revature.services.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reply")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReplyController {
    @Autowired
    ReplyService replyService;

    @GetMapping("/{postId}")
    public ResponseEntity<List<Reply>> getPostReplies(@PathVariable("postId") int postId){
        return ResponseEntity.ok().body(this.replyService.getPostReplies(postId));
    }
}
