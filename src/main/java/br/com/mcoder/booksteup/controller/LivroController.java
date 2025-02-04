package br.com.mcoder.booksteup.controller;

import br.com.mcoder.booksteup.dto.LivroDTO;
import br.com.mcoder.booksteup.entites.Livro;
import br.com.mcoder.booksteup.repository.LivroRepository;
import br.com.mcoder.booksteup.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping(value = "/{id}")
    public LivroDTO getById(@PathVariable Long id) {
        return livroService.findById(id);
    }

    @GetMapping
    public List<LivroDTO> getAll() {
        return livroService.findAll();
    }

    @PostMapping
    public LivroDTO insert(@RequestBody LivroDTO livroDTO){
        livroDTO = livroService.insert(livroDTO);
        return livroDTO;
    }
}
