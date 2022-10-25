package com.revature.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
@EqualsAndHashCode(of = "accountId")
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
    private Set<Post> likedPosts = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "liked_reply",
            joinColumns = @JoinColumn(name = "accountId"),
            inverseJoinColumns = @JoinColumn(name = "replyId"))
    private Set<Reply> likedReplies = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "disliked_post",
            joinColumns = @JoinColumn(name = "accountId"),
            inverseJoinColumns = @JoinColumn(name = "postId"))
    private Set<Post> dislikedPosts = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "disliked_reply",
            joinColumns = @JoinColumn(name = "accountId"),
            inverseJoinColumns = @JoinColumn(name = "replyId"))
    private Set<Reply> dislikedReplies = new HashSet<>();

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
