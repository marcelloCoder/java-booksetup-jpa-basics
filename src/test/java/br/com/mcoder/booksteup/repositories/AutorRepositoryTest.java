package br.com.mcoder.booksteup.repositories;

import br.com.mcoder.booksteup.entites.Autor;
import br.com.mcoder.booksteup.factory.AutorFactory;
import br.com.mcoder.booksteup.repository.AutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class AutorRepositoryTest {

    @Autowired
    private AutorRepository autorRepository;

    private long existingId;
    private long nonExistingId;
    private long countTotalAutor;

    @BeforeEach
    void setUp() throws Exception{
        existingId = 1L;
        nonExistingId = 100L;
        countTotalAutor = 4L;
    }

    @Test
    public void deleteShouldReturnEmptyWhenDeletesId(){
        autorRepository.deleteById(existingId);
        var result = autorRepository.findById(existingId);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void saveShouldPersistAutoIncrementsWhenIdIsNull(){
        Autor autor = AutorFactory.createAutor();
        autor.setId(null);

        autor = autorRepository.save(autor);

        Assertions.assertNotNull(autor.getId());
        Assertions.assertEquals(countTotalAutor + 1, autor.getId());
    }

    @Test
    public void findByIdShouldReturnOptionalEmptyWhenIdDoesNotExist(){
        Optional<Autor> result = autorRepository.findById(nonExistingId);

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void findByIdShouldReturnOptionalNotEmptyWhenIdExists(){
        Optional<Autor> result = autorRepository.findById(existingId);
        Assertions.assertTrue(result.isPresent());
    }

}
