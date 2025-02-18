package br.com.mcoder.booksteup.entites;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;


@Entity
@Table(name = "tb_livro")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Temporal(TemporalType.DATE)
    @Column(name = "ano_publicacao")
    private LocalDate anoPublicacao;

    @ManyToOne(fetch = FetchType.LAZY) // <-- Lazy significa que o autor só será carregado quando for acessado
    @JoinColumn(name = "autor_id")
    private Autor autor;
}
