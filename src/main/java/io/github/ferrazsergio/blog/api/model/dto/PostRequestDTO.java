package io.github.ferrazsergio.blog.api.model.dto;

import io.github.ferrazsergio.blog.api.model.entities.Comentario;
import io.github.ferrazsergio.blog.api.model.entities.Post;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record PostRequestDTO(
        @NotNull(message = "O titulo não pode ser nulo")
        String titulo,
        String conteudo,
        @NotNull(message = "O nome do autor não pode ser nulo")
        String nomeAutor,
        LocalDateTime dataDePublicacao,
        List<Comentario> comentarios
) {
    public Post toEntity(){
        return new Post(titulo,conteudo,nomeAutor,dataDePublicacao,comentarios);
    }
}
