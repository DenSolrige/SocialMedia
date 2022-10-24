package com.revature.dtos;

import com.revature.entities.Account;
import com.revature.entities.Post;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
public class PostInfo {
    private int postId;
    private String title;
    private String content;
    private int updoot;
    private long date;
    private boolean edited;
    private String author;
    private DootStatus dootStatus = DootStatus.NODOOT;

    public PostInfo(Post post) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.updoot = post.getLikes().size()-post.getDislikes().size();
        this.date = post.getDate();
        this.edited = post.isEdited();
        this.author = post.getAuthor().getUsername();
    }
    public PostInfo(Post post, Account requestingUser) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.updoot = post.getLikes().size()-post.getDislikes().size();
        this.date = post.getDate();
        this.edited = post.isEdited();
        this.author = post.getAuthor().getUsername();
        if(requestingUser.getLikedPosts().contains(post)){
            this.dootStatus = DootStatus.UPDOOT;
        }
        if(requestingUser.getDislikedPosts().contains(post)){
            this.dootStatus = DootStatus.DOWNDOOT;
        }
    }
}
