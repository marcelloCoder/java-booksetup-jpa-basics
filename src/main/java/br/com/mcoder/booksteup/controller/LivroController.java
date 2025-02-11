package br.com.mcoder.booksteup.controller;

import br.com.mcoder.booksteup.dto.LivroDTO;
import br.com.mcoder.booksteup.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<LivroDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(livroService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<LivroDTO>> getAll(@PageableDefault(size = 3)Pageable pageable) {
        Page<LivroDTO> livroDTOPage = livroService.findAll(pageable);
        return ResponseEntity.ok().body(livroDTOPage);
    }

    @PostMapping
    public LivroDTO insert(@RequestBody LivroDTO livroDTO) {
        livroDTO = livroService.insert(livroDTO);
        return livroDTO;
    }
}
