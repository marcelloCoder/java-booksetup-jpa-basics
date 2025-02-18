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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


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
    public ResponseEntity<Page<LivroDTO>> getAll(@PageableDefault(size = 10)Pageable pageable) {
        Page<LivroDTO> livroDTOPage = livroService.findAll(pageable);
        return ResponseEntity.ok().body(livroDTOPage);
    }

    @PostMapping
    public ResponseEntity<LivroDTO> insert(@RequestBody LivroDTO livroDTO) {
        livroDTO = livroService.insert(livroDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(livroDTO.id()).toUri();
        return ResponseEntity.created(uri).body(livroDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<LivroDTO> update(@PathVariable Long id, @RequestBody LivroDTO livroDTO) {
        livroDTO = livroService.update(id, livroDTO);
        return ResponseEntity.ok(livroDTO);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        livroService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
