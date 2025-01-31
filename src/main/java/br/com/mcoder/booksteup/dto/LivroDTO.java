package br.com.mcoder.booksteup.dto;

import br.com.mcoder.booksteup.entites.Livro;

import java.util.Date;

public record LivroDTO(Long id, String titulo, Date anoPublicacao, String autorNome) {

    // Construtor personalizado
    public LivroDTO(Livro entity) {
        this(
                entity.getId(),
                entity.getTitulo(),
                entity.getAno_publicacao(),
                entity.getAutor() != null ? entity.getAutor().getNome() : null
        );
    }
}

