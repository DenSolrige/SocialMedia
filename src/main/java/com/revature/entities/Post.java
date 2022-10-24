package com.revature.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId = 0;
    private String title;
    private String content;
    private long date;
    private boolean edited = false;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Reply> replies;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account author;

    @ManyToMany(mappedBy = "likedPosts")
    private Set<Account> likes;

    @ManyToMany(mappedBy = "dislikedPosts")
    private Set<Account> dislikes;

    public Post(String title, String content, Account author, long date) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date=" + new Date(date * 1000) +
                ", edited=" + edited +
                ", accountId=" + author.getAccountId() +
                '}';
    }
}
