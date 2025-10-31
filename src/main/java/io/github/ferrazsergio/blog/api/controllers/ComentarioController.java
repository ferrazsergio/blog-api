package io.github.ferrazsergio.blog.api.controllers;


import io.github.ferrazsergio.blog.api.model.dto.ComentarioRequestDTO;
import io.github.ferrazsergio.blog.api.model.dto.ComentarioResponseDTO;
import io.github.ferrazsergio.blog.api.services.ComentarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/posts/{postId}/comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService;

    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    // RF004 — Criar Comentário em um Post existente
    @PostMapping
    public ResponseEntity<ComentarioResponseDTO> criar(
            @PathVariable Integer postId,
            @Valid @RequestBody ComentarioRequestDTO dto,
            UriComponentsBuilder uriBuilder) {

        ComentarioResponseDTO createdComment = comentarioService.adicionarComentario(postId, dto);
        var uri = uriBuilder.path("/posts/{postId}/comentarios/{id}")
                .buildAndExpand(postId, createdComment.id()).toUri();

        return ResponseEntity.created(uri).body(createdComment);
    }

    // RF005 — Listar todos os comentários de um Post específico
    @GetMapping
    public ResponseEntity<List<ComentarioResponseDTO>> listarPorPost(@PathVariable Integer postId) {
        return ResponseEntity.ok(comentarioService.listarPorPost(postId));
    }
}

