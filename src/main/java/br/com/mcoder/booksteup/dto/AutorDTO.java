package br.com.mcoder.booksteup.dto;

import br.com.mcoder.booksteup.entites.Autor;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record AutorDTO(
        Long id,
        String nome,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        Date dataNascimento) {

    // Construtor personalizado
    public AutorDTO(Autor entity) {
        this(entity.getId(), entity.getNome(), entity.getDataNascimento());
    }
}
