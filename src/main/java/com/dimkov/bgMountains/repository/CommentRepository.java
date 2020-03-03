package com.dimkov.bgMountains.repository;

import com.dimkov.bgMountains.domain.entities.Comment;
import com.dimkov.bgMountains.domain.entities.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
    List<Comment> findAllByFreelancer(Freelancer freelancer);
}
