package io.github.ferrazsergio.blog.api.model.dto;

import io.github.ferrazsergio.blog.api.model.entities.Comentario;
import io.github.ferrazsergio.blog.api.model.entities.Post;

import java.time.LocalDateTime;
import java.util.List;

public record PostResponseDTO(
        Integer id,
        String titulo,
        String conteudo,
        String nomeAutor,
        LocalDateTime dataDePublicacao,
        List<Comentario> comentarios
) {
    public static PostResponseDTO fromEntity(Post post) {
        return new PostResponseDTO(
                post.getId(),
                post.getTitulo(),
                post.getConteudo(),
                post.getNomeAutor(),
                post.getDataDePublicacao(),
                post.getComentarios()
        );
    }
}
