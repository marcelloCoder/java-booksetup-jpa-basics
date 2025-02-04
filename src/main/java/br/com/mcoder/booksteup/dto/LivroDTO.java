package br.com.mcoder.booksteup.dto;

import br.com.mcoder.booksteup.entites.Livro;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record LivroDTO(Long id,
                       String titulo,
                       @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
                       Date anoPublicacao,
                       String autorNome) {

    // Construtor personalizado
    public LivroDTO(Livro entity) {
        this(
                entity.getId(),
                entity.getTitulo(),
                entity.getAnoPublicacao(),
                entity.getAutor() != null ? entity.getAutor().getNome() : null
        );
    }
}

