package br.com.mcoder.booksteup.services;

import br.com.mcoder.booksteup.dto.LivroDTO;
import br.com.mcoder.booksteup.entites.Autor;
import br.com.mcoder.booksteup.entites.Livro;
import br.com.mcoder.booksteup.factory.LivroFactory;
import br.com.mcoder.booksteup.repository.AutorRepository;
import br.com.mcoder.booksteup.repository.LivroRepository;
import br.com.mcoder.booksteup.service.LivroService;
import br.com.mcoder.booksteup.service.exceptions.DatabaseException;
import br.com.mcoder.booksteup.service.exceptions.EntityNotFoundException;
import br.com.mcoder.booksteup.service.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class LivroServiceTest {

    @InjectMocks
    private LivroService service;

    @Mock
    private LivroRepository repository;

    @Mock
    private AutorRepository autorRepository;

    private long existingId;
    private long nonExistingId;
    private long dependentId;
    private PageImpl<Livro> page;
    private Livro livro;
    private Autor autor;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
        dependentId = 3L;
        livro = LivroFactory.createLivro();
        autor = LivroFactory.createAutor();
        page = new PageImpl<>(List.of(livro));

        Mockito.lenient().when(repository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);
        Mockito.lenient().when(repository.save(ArgumentMatchers.any())).thenReturn(livro);
        Mockito.lenient().when(repository.findById(existingId)).thenReturn(Optional.of(livro));
        Mockito.lenient().when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
        Mockito.lenient().when(repository.getReferenceById(existingId)).thenReturn(livro);
        Mockito.lenient().when(repository.getReferenceById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
        Mockito.lenient().doNothing().when(repository).deleteById(existingId);
        Mockito.lenient().doThrow(DatabaseException.class).when(repository).deleteById(dependentId);
        Mockito.lenient().when(repository.existsById(existingId)).thenReturn(true);
        Mockito.lenient().when(repository.existsById(nonExistingId)).thenReturn(false);
        Mockito.lenient().when(repository.existsById(dependentId)).thenReturn(true);
        Mockito.when(autorRepository.findByNome(livro.getAutor().getNome())).thenReturn(Optional.of(autor)); // <-- Mockando chamada ao AutorRepository
    }


    @Test
    public void findAllPagedShouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<LivroDTO> result = service.findAll(pageable);
        assertNotNull(result);
        Mockito.verify(repository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {
        assertDoesNotThrow(() -> {
            service.delete(existingId);
        });
        Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
    }

    @Test
    public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
        assertThrows(DatabaseException.class, () -> {
            service.delete(dependentId);
        });
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });
    }

    @Test
    public void findByIdShouldReturnLivroDTOWhenIdExists() {
        LivroDTO result = service.findById(existingId);
        assertNotNull(result);
        assertEquals(livro.getId(), result.id());
        Mockito.verify(repository, Mockito.times(1)).findById(existingId);
    }

    @Test
    public void findByIdShouldThrowEntityNotFoundExceptionWhenIdDoesNotExist() {
        assertThrows(EntityNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });
        Mockito.verify(repository, Mockito.times(1)).findById(nonExistingId);
    }

    @Test
    public void updateShouldReturnLivroDTOWhenIdExists() {
        LivroDTO dto = LivroFactory.createLivroDto();
        LivroDTO result = service.update(existingId, dto);
        assertNotNull(result);
        assertEquals(livro.getId(), result.id());
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        LivroDTO dto = LivroFactory.createLivroDto();
        assertThrows(ResourceNotFoundException.class, () -> {
            service.update(nonExistingId, dto);
        });
    }
}

