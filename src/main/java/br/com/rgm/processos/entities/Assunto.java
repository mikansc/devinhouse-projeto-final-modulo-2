package br.com.rgm.processos.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
public class Assunto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Integer id;

    @Column(nullable = false)
    @Getter
    @Setter
    private String descricao;

    @Column(nullable = false)
    @Getter
    @Setter
    private Date dtCadastro;

    @Column(nullable = false, length = 1)
    @Getter
    @Setter
    private Character flAtivo;
}
