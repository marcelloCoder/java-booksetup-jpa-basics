package br.com.mcoder.booksteup.repository;

import br.com.mcoder.booksteup.entites.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNome(String nome);
}
