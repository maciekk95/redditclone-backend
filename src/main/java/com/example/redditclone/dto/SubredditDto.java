package com.example.redditclone.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubredditDto {
    private Long id;
    private String name;
    private String description;
    private Integer numberOfPosts;
}
