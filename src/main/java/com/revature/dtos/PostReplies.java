package com.revature.dtos;

import com.revature.entities.Reply;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostReplies {
    private List<Reply> replies;
}
