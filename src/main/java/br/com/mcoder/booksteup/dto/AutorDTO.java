package br.com.mcoder.booksteup.dto;

import br.com.mcoder.booksteup.entites.Autor;
import br.com.mcoder.booksteup.entites.Livro;

import java.util.Date;
import java.util.List;

public record AutorDTO(Long id, String nome, Date data_nascimento, List<Livro> livros) {
    public AutorDTO(Autor entity) {
        this(
                entity.getId(),
                entity.getNome(),
                entity.getData_nascimento(),
                entity.getLivros()
        );
    }
}
