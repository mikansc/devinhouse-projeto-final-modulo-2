package br.com.rgm.processos.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
public class Interessado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @Setter
    private String nmInteressado;

    @Column(unique=true, nullable = false, length = 50)
    @Setter
    private String nuIdentificacao;

    @Column(nullable = false)
    @Setter
    private Date dtNascimento;

    @Column(nullable = false, length = 1)
    @Setter
    private Character flAtivo;

}
