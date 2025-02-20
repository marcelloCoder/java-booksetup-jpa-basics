package br.com.mcoder.booksteup.dto;

import br.com.mcoder.booksteup.entites.Autor;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AutorDTO(Long id,
                       @Size(min = 3, message = "NOME DEVE CONTER PELO MENOS 3 CARACTERES")
                       @NotBlank(message = "CAMPO REQUERIDO")
                       String nome,
                       @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
                       LocalDate dataNascimento) {

    // Construtor personalizado
    public AutorDTO(Autor entity) {
        this(
                entity.getId(),
                entity.getNome(),
                entity.getDataNascimento()
        );
    }
}
