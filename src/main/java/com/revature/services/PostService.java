package com.revature.services;

import com.revature.dtos.PostInfo;
import com.revature.dtos.UpdatePost;
import com.revature.entities.Account;
import com.revature.entities.Post;
import com.revature.entities.Reply;
import com.revature.repos.AccountRepository;
import com.revature.repos.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    AccountRepository accountRepository;

    public List<Reply> getPostReplies(int postId){
        Optional<Post> post = this.postRepository.findById(postId);
        if(post.isPresent()){
            return post.get().getReplies();
        }else{
            throw new RuntimeException("Unable to find post with given id");
        }
    }

    public List<Post> getPosts(){
        return this.postRepository.findAll();
    }

    public Post createPost(Post post){
        return this.postRepository.save(post);
    }

    public void updatePost(UpdatePost post){
        Optional<Post> oldPost = this.postRepository.findById(post.getPostId());
        if(oldPost.isPresent()){
            oldPost.get().setTitle(post.getTitle());
            oldPost.get().setContent(post.getContent());
            oldPost.get().setEdited(true);
            this.postRepository.save(oldPost.get());
        }else{
            throw new RuntimeException("Post with given id not found");
        }
    }

    public void deletePostById(int postId){
        Optional<Post> oldPost = this.postRepository.findById(postId);
        if(oldPost.isPresent()){
            oldPost.get().setTitle("[deleted]");
            oldPost.get().setContent("[deleted]");
            oldPost.get().getAuthor().setAccountId(-1);
            this.postRepository.save(oldPost.get());
        }else{
            throw new RuntimeException("Post with given id not found");
        }
    }

    public void nodootPost(String username, int postId){
        Optional<Post> post = this.postRepository.findById(postId);
        Optional<Account> account = this.accountRepository.findByUsername(username);
        if(post.isPresent() && account.isPresent()){
            post.get().getLikes().remove(account.get());
            post.get().getDislikes().remove(account.get());
            this.postRepository.save(post.get());
        }else{
            throw new RuntimeException("Invalid post id or username given");
        }
    }

    public void updootPost(String username, int postId){
        Optional<Post> post = this.postRepository.findById(postId);
        Optional<Account> account = this.accountRepository.findByUsername(username);
        if(post.isPresent() && account.isPresent()){
            post.get().getDislikes().remove(account.get());
            post.get().getLikes().add(account.get());
            this.postRepository.save(post.get());
        }else{
            throw new RuntimeException("Invalid post id or username given");
        }
    }

    public void downdootPost(String username, int postId){
        Optional<Post> post = this.postRepository.findById(postId);
        Optional<Account> account = this.accountRepository.findByUsername(username);
        if(post.isPresent() && account.isPresent()){
            post.get().getLikes().remove(account.get());
            post.get().getDislikes().add(account.get());
            this.postRepository.save(post.get());
        }else{
            throw new RuntimeException("Invalid post id or username given");
        }
    }
}
