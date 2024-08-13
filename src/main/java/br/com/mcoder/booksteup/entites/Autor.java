package br.com.mcoder.booksteup.entites;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_autor")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private  String nome;

    @Temporal(TemporalType.DATE)
    private Date data_nascimento;

    @OneToMany(mappedBy = "autor")
    private List<Livro> livros = new ArrayList<>();
}
