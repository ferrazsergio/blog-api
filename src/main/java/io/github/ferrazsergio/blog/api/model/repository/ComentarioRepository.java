package io.github.ferrazsergio.blog.api.model.repository;

import io.github.ferrazsergio.blog.api.model.entities.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
    List<Comentario> findByPostId(Integer postId); // buscar comentários de um post
}
