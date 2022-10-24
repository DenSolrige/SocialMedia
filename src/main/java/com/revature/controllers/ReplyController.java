package com.revature.controllers;

import com.revature.services.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reply")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReplyController {

    @Autowired
    ReplyService replyService;

//    @GetMapping("/{postId}")
//    public ResponseEntity<List<ReplyInfo>> getPostReplies(@PathVariable("postId") int postId){
//        return ResponseEntity.ok().body(new PostReplies(this.replyService.getPostReplies(postId)).getReplies());
//    }
}
