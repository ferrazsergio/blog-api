package io.github.ferrazsergio.blog.api.model.dto;

import io.github.ferrazsergio.blog.api.model.entities.Comentario;
import io.github.ferrazsergio.blog.api.model.entities.Post;

import java.time.LocalDateTime;

public record ComentarioResponseDTO(
        Integer id,
         String texto,
         String nomeAutor,
         LocalDateTime dataCriacao,
         Post post
) {
    public static ComentarioResponseDTO fromEntity(Comentario comentario) {
        return new ComentarioResponseDTO(
                comentario.getId(),
                comentario.getTexto(),
                comentario.getNomeAutor(),
                comentario.getDataCriacao(),
                comentario.getPost()
        );
    }
}
