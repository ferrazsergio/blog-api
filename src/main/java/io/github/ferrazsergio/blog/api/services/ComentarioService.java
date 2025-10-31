package io.github.ferrazsergio.blog.api.services;

import io.github.ferrazsergio.blog.api.model.dto.ComentarioRequestDTO;
import io.github.ferrazsergio.blog.api.model.dto.ComentarioResponseDTO;
import io.github.ferrazsergio.blog.api.model.entities.Comentario;
import io.github.ferrazsergio.blog.api.model.entities.Post;
import io.github.ferrazsergio.blog.api.model.repository.ComentarioRepository;
import io.github.ferrazsergio.blog.api.model.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço responsável por gerenciar operações relacionadas aos comentários.
 */
@Service
@RequiredArgsConstructor
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final PostRepository postRepository;

    /**
     * Adiciona um novo comentário a um post existente.
     *
     * @param postId ID do post ao qual o comentário será vinculado.
     * @param dto    Dados do comentário a ser criado.
     * @return DTO representando o comentário salvo.
     */
    @Transactional
    public ComentarioResponseDTO adicionarComentario(Integer postId, ComentarioRequestDTO dto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post não encontrado para o ID: " + postId));

        Comentario comentario = new Comentario();
        comentario.setTexto(dto.texto());
        comentario.setNomeAutor(dto.nomeAutor());
        comentario.setPost(post);

        Comentario saved = comentarioRepository.save(comentario);
        return ComentarioResponseDTO.fromEntity(saved);
    }

    /**
     * Lista todos os comentários associados a um post específico.
     *
     * @param postId ID do post.
     * @return Lista de DTOs representando os comentários encontrados.
     */
    @Transactional
    public List<ComentarioResponseDTO> listarPorPost(Integer postId) {
        if (!postRepository.existsById(postId)) {
            throw new EntityNotFoundException("Post não encontrado para o ID: " + postId);
        }

        return comentarioRepository.findByPostId(postId)
                .stream()
                .map(ComentarioResponseDTO::fromEntity)
                .toList();
    }
}
