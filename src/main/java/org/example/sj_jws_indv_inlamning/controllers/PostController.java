package org.example.sj_jws_indv_inlamning.controllers;

import org.example.sj_jws_indv_inlamning.entities.Post;
import org.example.sj_jws_indv_inlamning.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getPosts());
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable long id) {
        return ResponseEntity.ok(postService.findPostById(id));
    }

    @PostMapping("/newpost")
    public ResponseEntity<Post> createPost(@RequestBody Post post, JwtAuthenticationToken authentication) {
        return new ResponseEntity<>(postService.addPost(post, authentication), HttpStatus.CREATED);
    }

    @PutMapping("/updatepost")
    public ResponseEntity<Post> updatePost(@RequestBody Post post, JwtAuthenticationToken authentication) {
        return ResponseEntity.ok(postService.updatePost(post, authentication));
    }

    @DeleteMapping("/deletepost/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id, JwtAuthenticationToken authentication) {
        return ResponseEntity.ok(postService.deletePost(id, authentication));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getPostCount() {
        return ResponseEntity.ok(postService.countPosts());
    }
}
