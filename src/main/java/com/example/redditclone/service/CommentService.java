package com.example.redditclone.service;

import com.example.redditclone.dto.CommentDto;
import com.example.redditclone.dto.PostDto;
import com.example.redditclone.exception.ResourceNotFoundException;
import com.example.redditclone.model.Comment;
import com.example.redditclone.model.Post;
import com.example.redditclone.model.User;
import com.example.redditclone.repository.CommentRepository;
import com.example.redditclone.mapper.CommentMapper;
import com.example.redditclone.repository.PostRepository;
import com.example.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;
    private final AuthorizationService authorizationService;
    private final UserRepository userRepository;

    public void addComment(CommentDto commentDto) {
        User currentUser = authorizationService.getCurrentUser();
        Post post = postRepository.findById(commentDto.getPostId()).orElseThrow(() ->
                new ResourceNotFoundException("Post with id " + commentDto.getPostId() + " not found"));
        commentRepository.save(commentMapper.mapDtoToComment(commentDto, post, currentUser));
    }

    public List<CommentDto> findByCommentsByPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post with id " + postId + " not found"));
        return commentRepository.findByPost(post).stream().map(commentMapper::mapCommentToDto).collect(Collectors.toList());
    }

    public List<CommentDto> findByCommentsByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User with username " + username + " not found"));
        return commentRepository.findByUser(user).stream().map(commentMapper::mapCommentToDto).collect(Collectors.toList());
    }
    
}
