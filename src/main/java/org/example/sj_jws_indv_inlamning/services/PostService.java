package org.example.sj_jws_indv_inlamning.services;

import org.example.sj_jws_indv_inlamning.entities.Post;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;

public interface PostService {

    List<Post> getPosts();
    Post findPostById(Long id);
    Post addPost(Post post, JwtAuthenticationToken auth);
    Post updatePost(Post post, JwtAuthenticationToken auth);

    String deletePost(Long id, JwtAuthenticationToken auth);

    int countPosts();

}
