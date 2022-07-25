package com.example.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String text;
    private Date createdDate;
    private Long postId;
    private String userName;
}
