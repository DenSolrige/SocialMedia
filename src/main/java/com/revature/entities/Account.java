package com.revature.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountId = 0;
    private String username;
    private String password;

    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Post> posts;

    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Reply> reply;

    @ManyToMany
    @JoinTable(
            name = "liked_post",
            joinColumns = @JoinColumn(name = "accountId"),
            inverseJoinColumns = @JoinColumn(name = "postId"))
    private List<Post> likedPosts;

    @ManyToMany
    @JoinTable(
            name = "liked_reply",
            joinColumns = @JoinColumn(name = "accountId"),
            inverseJoinColumns = @JoinColumn(name = "replyId"))
    private List<Reply> likedReplies;

    @ManyToMany
    @JoinTable(
            name = "disliked_post",
            joinColumns = @JoinColumn(name = "accountId"),
            inverseJoinColumns = @JoinColumn(name = "postId"))
    private List<Post> dislikedPosts;

    @ManyToMany
    @JoinTable(
            name = "disliked_reply",
            joinColumns = @JoinColumn(name = "accountId"),
            inverseJoinColumns = @JoinColumn(name = "replyId"))
    private List<Reply> dislikedReplies;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
