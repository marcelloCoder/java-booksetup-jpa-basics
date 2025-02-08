package br.com.mcoder.booksteup.controller;

import br.com.mcoder.booksteup.dto.AutorDTO;
import br.com.mcoder.booksteup.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @GetMapping(value = "/{name}")
    public AutorDTO findByName(@PathVariable String name) {
        AutorDTO result = autorService.findByName(name);
        return result;
    }

    @GetMapping
    public List<AutorDTO> getAll() {
        return autorService.findAll();
    }

    @PostMapping
    public AutorDTO insert(@RequestBody AutorDTO autorDTO){
        return autorService.insert(autorDTO);
    }
}
