package io.github.ferrazsergio.blog.api.controllers;

import io.github.ferrazsergio.blog.api.model.dto.PostRequestDTO;
import io.github.ferrazsergio.blog.api.model.dto.PostResponseDTO;
import io.github.ferrazsergio.blog.api.services.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Controlador responsável pelos endpoints relacionados a Posts.
 *
 * Requisitos atendidos:
 * - RF001: Cadastro de Post
 * - RF002: Listagem paginada de Posts
 * - RF003: Busca de Post por ID
 * - RF006: Exclusão de Post (com comentários em cascata)
 */
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // RF001 — Criar Post
    @PostMapping
    public ResponseEntity<PostResponseDTO> criar(@Valid @RequestBody PostRequestDTO dto,
                                                 UriComponentsBuilder uriBuilder) {
        PostResponseDTO createdPost = postService.save(dto);
        var uri = uriBuilder.path("/posts/{id}")
                .buildAndExpand(createdPost.id())
                .toUri();
        return ResponseEntity.created(uri).body(createdPost);
    }

    // RF002 — Listar todos os Posts (com paginação)
    @GetMapping
    public ResponseEntity<Page<PostResponseDTO>> listar(Pageable pageable) {
        return ResponseEntity.ok(postService.listar(pageable));
    }

    // RF003 — Buscar Post por ID
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(postService.findById(id));
    }

    // RF004 — Atualizar Post existente
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDTO> atualizar(@PathVariable Integer id,
                                                     @Valid @RequestBody PostRequestDTO dto) {
        PostResponseDTO updated = postService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    // RF006 — Excluir Post (e comentários em cascata)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        postService.deleteById(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }
}
