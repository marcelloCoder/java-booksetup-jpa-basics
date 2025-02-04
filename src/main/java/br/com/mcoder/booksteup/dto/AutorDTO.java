package br.com.mcoder.booksteup.dto;

import br.com.mcoder.booksteup.entites.Autor;
import br.com.mcoder.booksteup.entites.Livro;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public record AutorDTO(Long id, String nome, Date data_nascimento, List<String> livros) {
    public AutorDTO(Autor entity) {
        this(
                entity.getId(),
                entity.getNome(),
                entity.getData_nascimento(),

                //Evita problemas de Lazy Loading 🚀
                //
                //O Hibernate pode tentar carregar a lista de Livro de forma tardia (Lazy Loading) quando o DTO já está sendo usado, causando erros como LazyInitializationException.
                //Evita expor dados desnecessários 🔐
                //
                //Antes, você estava retornando todos os detalhes dos livros no autor. Agora, você retorna apenas os títulos.
                //Melhora a performance ⚡
                //
                //Evita carregar informações que não serão usadas.
                entity.getLivros() != null ?
                        entity.getLivros().stream()
                                .map(livro -> livro.getTitulo()) // Pegando só os títulos dos livros
                                .collect(Collectors.toList())
                        : null
        );
    }
}
