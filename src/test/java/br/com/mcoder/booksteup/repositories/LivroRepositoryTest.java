package br.com.mcoder.booksteup.repositories;

import br.com.mcoder.booksteup.entites.Livro;
import br.com.mcoder.booksteup.factory.LivroFactory;
import br.com.mcoder.booksteup.repository.LivroRepository;
import br.com.mcoder.booksteup.service.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

@DataJpaTest
public class LivroRepositoryTest {

    @Autowired
    private LivroRepository livroRepository;

    private long existingId;
    private long nonExistingId;
    private long countTotalBooks;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
        countTotalBooks = 8L;
    }

    @Test
    public void deleteShouldDeleteObjectWhenIDExists(){
        livroRepository.deleteById(existingId);
        Optional<Livro> result =livroRepository.findById(existingId);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull() {
        Livro livro = LivroFactory.createLivro();
        livro.setId(null);

        livro = livroRepository.save(livro);

        Assertions.assertNotNull(livro.getId());
        Assertions.assertEquals(countTotalBooks + 1, livro.getId());

    }

    @Test
    void findByIdShouldReturnOptionalEmptyWhenIdDoesNotExist() {
        Optional<Livro> result = livroRepository.findById(nonExistingId);

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    void findByIdShouldReturnOptionalNotEmptyWhenIdExists() {
        Optional<Livro> result = livroRepository.findById(existingId);

        Assertions.assertTrue(result.isPresent());
    }


    @Test
    public void advancedRandomTest() {

        for (int i = 0; i < 5; i++) {
            Livro livro = LivroFactory.createLivro();
            livro.setId(null);
            livro.setTitulo("Random Book " + i);

            livro = livroRepository.save(livro);

            Assertions.assertNotNull(livro.getId());
            Assertions.assertEquals("Random Book " + i, livro.getTitulo());
        }

        long totalBooks = livroRepository.count();
        Assertions.assertEquals(countTotalBooks + 5, totalBooks);
    }
}
