package br.com.mcoder.booksteup.service;

import br.com.mcoder.booksteup.dto.AutorDTO;
import br.com.mcoder.booksteup.entites.Autor;
import br.com.mcoder.booksteup.repository.AutorRepository;
import br.com.mcoder.booksteup.service.exceptions.DatabaseException;
import br.com.mcoder.booksteup.service.exceptions.EntityNotFoundException;
import br.com.mcoder.booksteup.service.exceptions.NoSuchElementException;
import br.com.mcoder.booksteup.service.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AutorService {

    private final AutorRepository autorRepository;

    @Transactional(readOnly = true)
    public List<AutorDTO> findByNomeAproximado(String name) {
        try {
            List<Autor> autores = autorRepository.findByNomeAproximado(name);
            if (autores.isEmpty()) {
                throw new NoSuchElementException("Nenhum autor encontrado com esse nome");
            }
            return autores.stream().map(x -> new AutorDTO(x)).toList();
        }catch (NoSuchElementException e){
            throw new NoSuchElementException("Nenhum autor encontrado com esse nome");
        }

    }

    @Transactional(readOnly = true)
    public Page<AutorDTO> findAll(Pageable pageable) {
        Page<Autor> autorList = autorRepository.findAll(pageable);
        log.info("TODOS AUTORES BUSCADOS COM SUCESSO!");
        return autorList.map(x -> new AutorDTO(x));
    }

    @Transactional
    public AutorDTO insert(AutorDTO autorDTO) {
        Autor autor = Autor.builder().nome(autorDTO.nome()).dataNascimento(autorDTO.dataNascimento()).build();
        autorRepository.save(autor);
        log.info("NOVO AUTOR SALVO COM SUCESSO!");
        AutorDTO result = copy(autor, autorDTO);
        return result;
    }

    @Transactional
    public AutorDTO update(Long id, AutorDTO autorDTO) {
        try {
            Autor autor = autorRepository.getReferenceById(id);
            autor.setNome(autorDTO.nome());
            autor.setDataNascimento(autorDTO.dataNascimento());
            autorRepository.save(autor);
            log.info("AUTOR ATUALIZADO COM SUCESSO!");
            return new AutorDTO(autor);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }

    }

    @Transactional
    public void delete(Long id) {
        if (!autorRepository.existsById(id)) {
            throw new NoSuchElementException("Autor com ID " + id + " não encontrado");
        }
        try {
            autorRepository.deleteById(id);
            log.info("AUTOR DELETADO COM SUCESSO!");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha na integridade dos dados: não é possível deletar o autor com ID " + id);
        }
    }


    private AutorDTO copy(Autor autor, AutorDTO autorDTO) {
        return new AutorDTO(autor.getId(), autor.getNome(), autor.getDataNascimento());
    }

}
