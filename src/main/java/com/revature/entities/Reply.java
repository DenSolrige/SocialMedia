package com.revature.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reply")
@EqualsAndHashCode(of = "replyId")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int replyId;
    private String content;
    private long date = 0;
    private boolean edited = false;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account author;

    @ManyToMany(mappedBy = "likedReplies")
    private Set<Account> likes = new HashSet<>();

    @ManyToMany(mappedBy = "dislikedReplies")
    private Set<Account> dislikes = new HashSet<>();

    public Reply(String content, Post post, Account author, long date) {
        this.content = content;
        this.post = post;
        this.author = author;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "replyId=" + replyId +
                ", content='" + content + '\'' +
                ", date=" + new Date(date * 1000) +
                ", edited=" + edited +
                ", post=" + post.getTitle() +
                ", author=" + author.getUsername() +
                '}';
    }
}
