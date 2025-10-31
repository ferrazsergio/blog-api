package io.github.ferrazsergio.blog.api.services;

import io.github.ferrazsergio.blog.api.model.dto.PostRequestDTO;
import io.github.ferrazsergio.blog.api.model.dto.PostResponseDTO;
import io.github.ferrazsergio.blog.api.model.entities.Post;
import io.github.ferrazsergio.blog.api.model.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço responsável pelas operações de criação, leitura, atualização e exclusão de Posts.
 */
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    /**
     * Lista todos os posts de forma paginada.
     *
     * @param pageable parâmetros de paginação (page, size, sort)
     * @return página contendo os posts mapeados para DTOs
     */
    public Page<PostResponseDTO> listar(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(PostResponseDTO::fromEntity);
    }

    /**
     * Busca um post específico pelo ID.
     *
     * @param id identificador do post
     * @return DTO representando o post encontrado
     */
    public PostResponseDTO findById(Integer id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post não encontrado para o ID: " + id));
        return PostResponseDTO.fromEntity(post);
    }

    /**
     * Cria um novo post.
     *
     * @param postRequestDTO dados para criação do post
     * @return DTO representando o post salvo
     */
    @Transactional
    public PostResponseDTO save(PostRequestDTO postRequestDTO) {
        Post post = postRequestDTO.toEntity();
        Post savedPost = postRepository.save(post);
        return PostResponseDTO.fromEntity(savedPost);
    }

    /**
     * Atualiza os dados de um post existente.
     *
     * @param id  identificador do post
     * @param dto dados atualizados
     * @return DTO representando o post atualizado
     */
    @Transactional
    public PostResponseDTO update(Integer id, PostRequestDTO dto) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post não encontrado para o ID: " + id));

        existingPost.setTitulo(dto.titulo());
        existingPost.setConteudo(dto.conteudo());
        existingPost.setNomeAutor(dto.nomeAutor());

        Post updatedPost = postRepository.save(existingPost);
        return PostResponseDTO.fromEntity(updatedPost);
    }

    /**
     * Exclui um post pelo ID.
     * Se o post não existir, lança uma exceção.
     *
     * @param id identificador do post
     */
    @Transactional
    public void deleteById(Integer id) {
        if (!postRepository.existsById(id)) {
            throw new EntityNotFoundException("Post não encontrado para o ID: " + id);
        }
        postRepository.deleteById(id);
    }
}
