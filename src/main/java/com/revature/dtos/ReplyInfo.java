package com.revature.dtos;

import com.revature.entities.Account;
import com.revature.entities.Reply;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReplyInfo {
    private int replyId;
    private String content;
    private int updoot;
    private long date;
    private boolean edited;
    private String author;
    private DootStatus dootStatus = DootStatus.NODOOT;

    public ReplyInfo(Reply reply) {
        this.replyId = reply.getReplyId();
        this.content = reply.getContent();
        this.updoot = reply.getLikes().size()-reply.getDislikes().size();
        this.date = reply.getDate();
        this.edited = reply.isEdited();
        this.author = reply.getAuthor().getUsername();
    }

    public ReplyInfo(Reply reply, Account requestingUser) {
        this.replyId = reply.getReplyId();
        this.content = reply.getContent();
        this.updoot = reply.getLikes().size()-reply.getDislikes().size();
        this.date = reply.getDate();
        this.edited = reply.isEdited();
        this.author = reply.getAuthor().getUsername();
        if(requestingUser.getLikedReplies().contains(reply)){
            this.dootStatus = DootStatus.UPDOOT;
        }
        if(requestingUser.getDislikedReplies().contains(reply)){
            this.dootStatus = DootStatus.DOWNDOOT;
        }
    }
}
