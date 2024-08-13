package br.com.mcoder.booksteup.entites;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Table(name = "tb_livro")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titulo;

    @Temporal(TemporalType.DATE)
    private Date ano_publicacao;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;
}
