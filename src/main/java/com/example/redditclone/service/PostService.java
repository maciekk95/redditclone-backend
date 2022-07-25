package com.example.redditclone.service;

import com.example.redditclone.dto.PostDto;
import com.example.redditclone.exception.ResourceNotFoundException;
import com.example.redditclone.mapper.PostMapper;
import com.example.redditclone.model.Post;
import com.example.redditclone.model.Subreddit;
import com.example.redditclone.model.User;
import com.example.redditclone.repository.PostRepository;
import com.example.redditclone.repository.SubredditRepository;
import com.example.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    private final PostMapper postMapper;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final AuthorizationService authorizationService;


    public Post createPost(PostDto postDto) {
        User currentUser = authorizationService.getCurrentUser();
        Subreddit subreddit = subredditRepository.findByName(postDto.getSubredditName()).orElseThrow(
                () -> new ResourceNotFoundException("Subreddit with name " + postDto.getSubredditName() + " not found"));

        return postRepository.save(postMapper.mapDtoToPost(postDto, subreddit, currentUser));
    }

    public PostDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post with id " + id + " not found"));
        return postMapper.mapPostToDto(post);
    }

    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream().map(postMapper::mapPostToDto).collect(Collectors.toList());
    }

    public List<PostDto> getAllPostsBySubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Subreddit with id " + id + " not found"));
        return postRepository.findAllBySubreddit(subreddit).stream().map(postMapper::mapPostToDto).collect(Collectors.toList());
    }

    public List<PostDto> getAllPostsByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("User with username " + username + " not found"));
        return postRepository.findAllByUser(user).stream().map(postMapper::mapPostToDto).collect(Collectors.toList());
    }
}
