package br.com.mcoder.booksteup.service;

import br.com.mcoder.booksteup.dto.LivroDTO;
import br.com.mcoder.booksteup.entites.Livro;
import br.com.mcoder.booksteup.repository.LivroRepository;
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
public class LivroService {
    @Autowired
    private LivroRepository livroRepository;

    @Transactional(readOnly = true)
    public LivroDTO findById(Long id){
        Optional<Livro> result = livroRepository.findById(id);
        if (result.isPresent()){
            Livro livro = result.get();
            LivroDTO livroDTO = new LivroDTO(livro);
            return livroDTO;
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<LivroDTO> findAll(){
        List<Livro> livrosDTO = livroRepository.findAll();
        return livrosDTO.stream().map(x -> new LivroDTO(x)).toList();
    }

    public LivroDTO insert(LivroDTO livroDTO){
        Livro livro = Livro.builder()
                .titulo(livroDTO.titulo())
                .ano_publicacao(livroDTO.anoPublicacao())
                //.autor(livroDTO.autorNome())
                .build();
        livroRepository.save(livro);
        log.info("LIVRO CRIADO COM SUCESSO!!");
        LivroDTO copy = copy(livro, livroDTO);
        return copy;
    }

    private LivroDTO copy(Livro livro, LivroDTO livroDTO){
        return new LivroDTO(
                livro.getId(),
                livro.getTitulo(),
                livro.getAno_publicacao(),
                livroDTO.autorNome()
        );
    }
}
