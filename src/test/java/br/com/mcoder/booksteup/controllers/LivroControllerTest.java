package br.com.mcoder.booksteup.controllers;

import br.com.mcoder.booksteup.controller.LivroController;
import br.com.mcoder.booksteup.dto.LivroDTO;
import br.com.mcoder.booksteup.factory.LivroFactory;
import br.com.mcoder.booksteup.service.LivroService;
import br.com.mcoder.booksteup.service.exceptions.DatabaseException;
import br.com.mcoder.booksteup.service.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LivroController.class)
public class LivroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LivroService service;

    @Autowired
    private ObjectMapper objectMapper;

    private long existingId;
    private long nonExistingId;
    private long dependentId;
    private LivroDTO livroDTO;
    private PageImpl<LivroDTO> page;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
        dependentId = 3L;

        livroDTO = LivroFactory.createLivroDto();
        page = new PageImpl<>(List.of(livroDTO));

        Mockito.when(service.findAll(ArgumentMatchers.any())).thenReturn(page);
        when(service.findById(existingId)).thenReturn(livroDTO);
        when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
        when(service.update(eq(existingId), any())).thenReturn(livroDTO);
        when(service.update(eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);
        when(service.insert(any())).thenReturn(livroDTO);

        doNothing().when(service).delete(existingId);
        doThrow(ResourceNotFoundException.class).when(service).delete(nonExistingId);
        doThrow(DatabaseException.class).when(service).delete(dependentId);
    }

    @Test
    public void findAllShouldReturnPage() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/livros")
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());
    }

    @Test
    public void findByIdShouldReturnLivroDTOWhenIdExists() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/livros/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.id").exists());
        resultActions.andExpect(jsonPath("$.titulo").exists());
        resultActions.andExpect(jsonPath("$.anoPublicacao").exists());
        resultActions.andExpect(jsonPath("$.autorNome").exists());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/livros/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void updateShouldReturnLivroDTOWhenIdExists() throws Exception {
        String json = objectMapper.writeValueAsString(livroDTO);

        ResultActions resultActions = mockMvc.perform(put("/livros/{id}", existingId)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.id").exists());
        resultActions.andExpect(jsonPath("$.titulo").exists());
        resultActions.andExpect(jsonPath("$.anoPublicacao").exists());
        resultActions.andExpect(jsonPath("$.autorNome").exists());
    }

    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        String json = objectMapper.writeValueAsString(livroDTO);

        ResultActions resultActions = mockMvc.perform(put("/livros/{id}", nonExistingId)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldReturnCreated() throws Exception {
        String json = objectMapper.writeValueAsString(livroDTO);

        ResultActions resultActions = mockMvc.perform(post("/livros")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isCreated());
        resultActions.andExpect(jsonPath("$.id").exists());
        resultActions.andExpect(jsonPath("$.titulo").exists());
        resultActions.andExpect(jsonPath("$.anoPublicacao").exists());
        resultActions.andExpect(jsonPath("$.autorNome").exists());
    }

    @Test
    public void deleteShouldReturnNoContentWhenIdExists() throws Exception {
        ResultActions resultActions = mockMvc.perform(delete("/livros/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isNoContent());
        verify(service, times(1)).delete(existingId);
    }

    @Test
    public void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ResultActions resultActions = mockMvc.perform(delete("/livros/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isNotFound());
        verify(service, times(1)).delete(nonExistingId);
    }
}

