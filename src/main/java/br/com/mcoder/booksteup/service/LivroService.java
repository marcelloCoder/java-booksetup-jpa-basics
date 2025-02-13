package br.com.mcoder.booksteup.service;

import br.com.mcoder.booksteup.dto.AutorDTO;
import br.com.mcoder.booksteup.dto.LivroDTO;
import br.com.mcoder.booksteup.entites.Autor;
import br.com.mcoder.booksteup.entites.Livro;
import br.com.mcoder.booksteup.repository.AutorRepository;
import br.com.mcoder.booksteup.repository.LivroRepository;
import br.com.mcoder.booksteup.service.exceptions.EntityNotFoundException;
import br.com.mcoder.booksteup.service.exceptions.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LivroService {

    private final LivroRepository livroRepository;

    private final AutorRepository autorRepository;

    @Transactional(readOnly = true)
    public LivroDTO findById(Long id) {
        Optional<Livro> result = livroRepository.findById(id);
        Livro livro = result.orElseThrow(
                () -> new EntityNotFoundException("Entidade não encontrada!")
        );
        LivroDTO livroDTO = new LivroDTO(livro);
        log.info("LIVRO BUSCADO COM SUCESSO");
        return livroDTO;
    }

    @Transactional(readOnly = true)
    public Page<LivroDTO> findAll(Pageable pageable) {
        Page<Livro> livrosPage = livroRepository.findAll(pageable);
        log.info("TODOS OS LIVROS FORAM BUSCADOS COM SUCESSO");
        return livrosPage.map(x -> new LivroDTO(x));
    }

    @Transactional
    public LivroDTO insert(LivroDTO livroDTO) {
        Autor autor = autorRepository.findByNome(livroDTO.autorNome())
                .orElseThrow(() -> new NoSuchElementException("Autor não existe: " + livroDTO.autorNome()));

        Livro livro = Livro.builder()
                .titulo(livroDTO.titulo())
                .anoPublicacao(livroDTO.anoPublicacao())
                .autor(autor)
                .build();
        livroRepository.save(livro);
        log.info("LIVRO CRIADO COM SUCESSO!!");
        LivroDTO copy = copy(livro, livroDTO);
        return copy;
    }

    private LivroDTO copy(Livro livro, LivroDTO livroDTO) {
        return new LivroDTO(
                livro.getId(),
                livro.getTitulo(),
                livro.getAnoPublicacao(),
                livroDTO.autorNome()
        );
    }
}
