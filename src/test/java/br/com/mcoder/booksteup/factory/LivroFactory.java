package br.com.mcoder.booksteup.factory;

import br.com.mcoder.booksteup.dto.LivroDTO;
import br.com.mcoder.booksteup.entites.Autor;
import br.com.mcoder.booksteup.entites.Livro;

import java.time.LocalDate;

public class LivroFactory {

    public static Livro createLivro() {
        LocalDate localDate = LocalDate.now();
        Livro livro = new Livro(1L,"TDD",localDate,createAutor());
        return livro;
    }

    public static LivroDTO createLivroDto() {
        Livro livro = createLivro();
        return new LivroDTO(livro);
    }

    public static Autor createAutor() {
        LocalDate localDate = LocalDate.EPOCH;
        Autor autor = new Autor(1L,"Sebb",localDate);
        return autor;
    }
}
