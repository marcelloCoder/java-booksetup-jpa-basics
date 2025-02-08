package br.com.mcoder.booksteup.service;

import br.com.mcoder.booksteup.dto.AutorDTO;
import br.com.mcoder.booksteup.entites.Autor;
import br.com.mcoder.booksteup.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public AutorDTO findByName(String name) {
        Optional<Autor> result = autorRepository.findByNome(name);
        Autor autor = result.get();
        AutorDTO autorDTO = new AutorDTO(autor);
        return autorDTO;
    }

    @Transactional(readOnly = true)
    public List<AutorDTO> findAll() {
        List<Autor> autorList = autorRepository.findAll();
        log.info("TODOS AUTORES BUSCADOS COM SUCESSO!");
        return autorList.stream().map(x -> new AutorDTO(x)).toList();
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
