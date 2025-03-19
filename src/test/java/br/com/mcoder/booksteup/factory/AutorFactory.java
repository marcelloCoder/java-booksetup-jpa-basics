package br.com.mcoder.booksteup.factory;

import br.com.mcoder.booksteup.dto.AutorDTO;
import br.com.mcoder.booksteup.entites.Autor;

import java.time.LocalDate;

public class AutorFactory {

    public static Autor createAutor(){
        LocalDate localDate = LocalDate.EPOCH;
        Autor autor = new Autor(1L, "Bryan", localDate);
        return autor;
    }

    public static AutorDTO createAutorDto(){
        Autor autor = createAutor();
        return new AutorDTO(autor);
    }
}
