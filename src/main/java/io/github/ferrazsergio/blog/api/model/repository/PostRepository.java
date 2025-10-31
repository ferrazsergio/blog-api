package io.github.ferrazsergio.blog.api.model.repository;

import io.github.ferrazsergio.blog.api.model.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
}
