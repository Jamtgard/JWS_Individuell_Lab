package org.example.sj_jws_indv_inlamning.repositories;

import org.example.sj_jws_indv_inlamning.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRespository extends JpaRepository<Post, Long> {



}
