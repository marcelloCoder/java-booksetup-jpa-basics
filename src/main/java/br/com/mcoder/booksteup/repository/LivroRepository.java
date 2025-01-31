package br.com.mcoder.booksteup.repository;

import br.com.mcoder.booksteup.entites.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
}
