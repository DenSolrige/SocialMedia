package com.revature.services;

import com.revature.dtos.CreatePost;
import com.revature.dtos.CreateReply;
import com.revature.dtos.DootStatus;
import com.revature.entities.Account;
import com.revature.entities.Post;
import com.revature.entities.Reply;
import com.revature.repos.AccountRepository;
import com.revature.repos.PostRepository;
import com.revature.repos.ReplyRepository;
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

    @Autowired
    ReplyRepository replyRepository;

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

    public Reply createReply(Account account, int postId, CreateReply createReply){
        Optional<Post> replyingPost = this.postRepository.findById(postId);
        if(replyingPost.isPresent()){
            Reply reply = new Reply(createReply.getContent(), replyingPost.get(),account,System.currentTimeMillis()/1000);
            return this.replyRepository.save(reply);
        }else{
            throw new RuntimeException("No post found with given id");
        }
    }

    public Post updatePost(int postId, CreatePost post){
        Optional<Post> oldPost = this.postRepository.findById(postId);
        if(oldPost.isPresent()){
            oldPost.get().setTitle(post.getTitle());
            oldPost.get().setContent(post.getContent());
            oldPost.get().setEdited(true);
            return this.postRepository.save(oldPost.get());
        }else{
            throw new RuntimeException("Post with given id not found");
        }
    }

    public void deletePostById(int postId){
        Optional<Post> oldPost = this.postRepository.findById(postId);
        if(oldPost.isPresent()){
            oldPost.get().setTitle("[deleted]");
            oldPost.get().setContent("[deleted]");
            Optional<Account> deletedPostAccount = this.accountRepository.findById(-1);
            oldPost.get().setAuthor(deletedPostAccount.get());
            this.postRepository.save(oldPost.get());
        }else{
            throw new RuntimeException("Post with given id not found");
        }
    }

    public void dootPost(String username, int postId, DootStatus dootStatus){
        Optional<Post> optionalPost = this.postRepository.findById(postId);
        Optional<Account> optionalAccount = this.accountRepository.findByUsername(username);
        if(optionalPost.isPresent() && optionalAccount.isPresent()){
            Post post = optionalPost.get();
            Account account = optionalAccount.get();

            if(dootStatus.equals(DootStatus.UPDOOT)){
                post.getLikes().add(account);
                post.getDislikes().remove(account);
                account.getLikedPosts().add(post);
                account.getDislikedPosts().remove(post);
            }else if(dootStatus.equals(DootStatus.DOWNDOOT)){
                post.getLikes().remove(account);
                post.getDislikes().add(account);
                account.getLikedPosts().remove(post);
                account.getDislikedPosts().add(post);
            }else{
                post.getLikes().remove(account);
                post.getDislikes().remove(account);
                account.getLikedPosts().remove(post);
                account.getDislikedPosts().remove(post);
            }

            this.postRepository.save(post);
            this.accountRepository.save(account);
        }else{
            throw new RuntimeException("Invalid post id or username given");
        }
    }
}
