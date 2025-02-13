package br.com.mcoder.booksteup.service;

import br.com.mcoder.booksteup.dto.AutorDTO;
import br.com.mcoder.booksteup.entites.Autor;
import br.com.mcoder.booksteup.repository.AutorRepository;
import br.com.mcoder.booksteup.service.exceptions.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AutorService {

    private final AutorRepository autorRepository;

    @Transactional(readOnly = true)
    public AutorDTO findByName(String name) {
        Optional<Autor> result = autorRepository.findByNome(name);
        Autor autor = result.orElseThrow(
                () -> new NoSuchElementException("Autor com esse nome não existe")
        );
        AutorDTO autorDTO = new AutorDTO(autor);
        return autorDTO;
    }

    @Transactional(readOnly = true)
    public List<AutorDTO> findByNomeAproximado(String name) {
        List<Autor> autores = autorRepository.findByNomeAproximado(name);
        if (autores.isEmpty()) {
            throw new NoSuchElementException("Nenhum autor encontrado com esse nome");
        }
        return autores.stream().map(AutorDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public List<AutorDTO> findAll() {
        List<Autor> autorList = autorRepository.findAll();
        log.info("TODOS AUTORES BUSCADOS COM SUCESSO!");
        return autorList.stream().map(x -> new AutorDTO(x)).collect(Collectors.toList());
    }

    @Transactional
    public AutorDTO insert(AutorDTO autorDTO) {
        Autor autor = Autor.builder()
                .nome(autorDTO.nome())
                .dataNascimento(autorDTO.dataNascimento())
                .build();
        autorRepository.save(autor);
        log.info("NOVO AUTOR SALVO COM SUCESSO!");
        AutorDTO result = copy(autor, autorDTO);
        return result;
    }

    private AutorDTO copy(Autor autor, AutorDTO autorDTO) {
        return new AutorDTO(
                autor.getId(),
                autor.getNome(),
                autor.getDataNascimento()
        );
    }

}
