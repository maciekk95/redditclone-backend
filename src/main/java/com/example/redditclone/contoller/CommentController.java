package com.example.redditclone.contoller;

import com.example.redditclone.dto.CommentDto;
import com.example.redditclone.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity createComment(@RequestBody CommentDto commentDto) {
        commentService.addComment(commentDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentDto>> getAllCommentsByPost(@PathVariable Long postId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.findByCommentsByPost(postId));
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<List<CommentDto>> getAllCommentsByUsername(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.findByCommentsByUsername(username));
    }
}
