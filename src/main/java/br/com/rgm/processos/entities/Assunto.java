package br.com.rgm.processos.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
public class Assunto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @Setter
    private String descricao;

    @Column(nullable = false)
    @Setter
    private Date dtCadastro;

    @Column(nullable = false, length = 1)
    @Setter
    private Character flAtivo;
}
