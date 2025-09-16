package org.example.sj_jws_indv_inlamning.services;

import org.example.sj_jws_indv_inlamning.entities.Post;
import org.example.sj_jws_indv_inlamning.exceptions.AuthorizationDeniedException;
import org.example.sj_jws_indv_inlamning.exceptions.ResourceNotFoundException;
import org.example.sj_jws_indv_inlamning.repositories.PostRespository;
import org.example.sj_jws_indv_inlamning.services.utilities.MicroMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRespository postRespository;

    @Autowired
    public PostServiceImpl(PostRespository postRespository) {
        this.postRespository = postRespository;
    }

//-- CRUD --------------------------------------------------------------------------------------------------------------


    @Override
    public List<Post> getPosts() {
        if (postRespository.findAll().isEmpty()) {
            throw new ResourceNotFoundException("List", "posts", 0);
        }
        return postRespository.findAll();
    }

    @Override
    public Post findPostById(Long id) {
        return postRespository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Post", "id", id));
    }

    @Override
    public Post addPost(Post post, JwtAuthenticationToken auth) {
        post.setAuthorId(auth.getToken().getSubject());
        post.setAuthorUsername(auth.getToken().getClaimAsString("preferred_username"));

        validatePostForCreation(post);

        System.out.println(post.getAuthorId());

        return postRespository.save(post);
    }

    @Override
    public Post updatePost(Post post, JwtAuthenticationToken auth) {

        Post entity = postRespository.findById(post.getId()).orElseThrow(()->
                new ResourceNotFoundException("Post", "id", post.getId()));

        String sub = auth.getToken().getSubject();

        if (!sub.equals(entity.getAuthorId())){
            throw new AuthorizationDeniedException("Post","Owner", "update");
        }

        validatePost(post);

        entity.setTitle(post.getTitle());
        entity.setContent(post.getContent());

        return postRespository.save(entity);
    }

    @Override
    public String deletePost(Long id, JwtAuthenticationToken auth) {

        Post post = postRespository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Post", "id", id));

        String sub = auth.getToken().getSubject();
        boolean isAdmin = checkIfAdmin(auth);

        if (!isAdmin && !sub.equals(post.getAuthorId())) {
            throw new AuthorizationDeniedException("Post", "Admin & Owner", "Delete");
        }

        postRespository.delete(post);
        return "Deleted";
    }

    @Override
    public Long countPosts() {
        return postRespository.count();
    }

    //-- Functionality -------------------------------------------------------------------------------------------------

    private void validatePostForCreation(Post post) {
        MicroMethods.validateData("Post", "Title", post.getTitle());
        MicroMethods.validateData("Post", "Content", post.getContent());
        MicroMethods.validateData("Post", "Author Username", post.getAuthorUsername());
        MicroMethods.validateData("Post", "Author ID", post.getAuthorId());
    }

    private void validatePost(Post post) {
        MicroMethods.validateData("Post", "Title", post.getTitle());
        MicroMethods.validateData("Post", "Content", post.getContent());
    }

    private boolean checkIfAdmin(JwtAuthenticationToken auth) {
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_myclient_ADMIN"));
    }

}
