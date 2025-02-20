package br.com.mcoder.booksteup.controller;

import br.com.mcoder.booksteup.dto.AutorDTO;
import br.com.mcoder.booksteup.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @GetMapping(value = "/{name}")
    public ResponseEntity<List<AutorDTO>> findByNomeAproximado(@PathVariable String name) {
        List<AutorDTO> result = autorService.findByNomeAproximado(name);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<Page<AutorDTO>> getAll(@PageableDefault(size = 10) Pageable pageable) {
        Page<AutorDTO> result = autorService.findAll(pageable);
        return ResponseEntity.ok(result);
    }


    @PostMapping
    public ResponseEntity<AutorDTO> insert(@RequestBody AutorDTO autorDTO) {
        AutorDTO result = autorService.insert(autorDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.id()).toUri();
        return ResponseEntity.created(uri).body(result);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AutorDTO> update(@PathVariable Long id, @RequestBody AutorDTO autorDTO) {
        AutorDTO result = autorService.update(id, autorDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        autorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
