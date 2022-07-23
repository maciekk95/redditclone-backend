package com.example.redditclone.mapper;

import com.example.redditclone.dto.PostDto;
import com.example.redditclone.model.Post;
import com.example.redditclone.model.Subreddit;
import com.example.redditclone.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    PostDto mapPostToDto(Post post);

    @Mapping(target = "createdDate", expression = "java(java.util.Date.from(java.time.Instant.now()))")
    @Mapping(target = "id", source = "postDto.id")
    @Mapping(target = "name", source = "postDto.name")
    @Mapping(target = "description", source = "postDto.description")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "user", source = "user")
    Post mapDtoToPost(PostDto postDto, Subreddit subreddit, User user);
}


