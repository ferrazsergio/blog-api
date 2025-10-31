package io.github.ferrazsergio.blog.api.model.dto;

import io.github.ferrazsergio.blog.api.model.entities.Comentario;

public record ComentarioRequestDTO(String texto, String nomeAutor) {
    public Comentario toEntity(){
        return new Comentario(texto,nomeAutor);
    }
}

