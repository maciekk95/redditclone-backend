package com.example.redditclone.service;

import com.example.redditclone.dto.PostDto;
import com.example.redditclone.exception.ResourceNotFoundException;
import com.example.redditclone.mapper.PostMapper;
import com.example.redditclone.model.Post;
import com.example.redditclone.model.Subreddit;
import com.example.redditclone.model.User;
import com.example.redditclone.repository.PostRepository;
import com.example.redditclone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    private final PostMapper postMapper;
    private final SubredditRepository subredditRepository;
    private final AuthorizationService authorizationService;


    public Post save(PostDto postDto) {
        User currentUser = authorizationService.getCurrentUser();
        Subreddit subreddit = subredditRepository.findByName(postDto.getSubredditName()).orElseThrow(
                () -> new ResourceNotFoundException("Subreddit with name " + postDto.getSubredditName() + " not found"));

        return postRepository.save(postMapper.mapDtoToPost(postDto, subreddit, currentUser));
    }
}
