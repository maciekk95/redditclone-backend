package com.example.redditclone.mapper;

import com.example.redditclone.dto.SubredditDto;
import com.example.redditclone.model.Post;
import com.example.redditclone.model.Subreddit;
import com.example.redditclone.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

    SubredditMapper INSTANCE = Mappers.getMapper( SubredditMapper.class );

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    SubredditDto mapSubredditToDto(Subreddit subreddit);

    default Integer mapPosts(List<Post> posts) {
        return posts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "id", source = "subredditDto.id")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "createdDate", expression = "java(java.util.Date.from(java.time.Instant.now()))")
    Subreddit mapDtoToSubreddit(SubredditDto subredditDto, User user);
}
