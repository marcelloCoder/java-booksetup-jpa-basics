package br.com.mcoder.booksteup.controller;

import br.com.mcoder.booksteup.dto.AutorDTO;
import br.com.mcoder.booksteup.service.AutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/autores")
@RequiredArgsConstructor
public class AutorController {

    @Autowired
    private final AutorService autorService;

    @GetMapping(value = "/{nome}")
    public AutorDTO findByName(String name) {
        AutorDTO result = autorService.findByName(name);
        return result;
    }

    @GetMapping
    public List<AutorDTO> getAll() {
        return autorService.findAll();
    }
}
