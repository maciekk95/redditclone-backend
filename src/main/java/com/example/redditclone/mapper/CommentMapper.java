package com.example.redditclone.mapper;

import com.example.redditclone.dto.CommentDto;
import com.example.redditclone.model.Comment;
import com.example.redditclone.model.Post;
import com.example.redditclone.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "id", source = "comment.id")
    @Mapping(target = "text", source = "comment.text")
    @Mapping(target = "createdDate", source = "comment.createdDate")
    @Mapping(target = "postId", source = "post.id")
    @Mapping(target = "userName", source = "user.username")
    CommentDto mapCommentToDto(Comment comment);

    @Mapping(target = "id", source = "commentDto.id")
    @Mapping(target = "text", source = "commentDto.text")
    @Mapping(target = "createdDate", expression = "java(java.util.Date.from(java.time.Instant.now()))")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    Comment mapDtoToComment(CommentDto commentDto, Post post, User user);
}
