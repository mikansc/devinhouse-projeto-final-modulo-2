package br.com.rgm.processos.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Processo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = true)
    @Setter
    private Integer nuProcesso;

    @Column(nullable = false, length = 4)
    @Setter
    private String sgOrgaoSetor;

    @Column(nullable = false, length = 4)
    @Setter
    private String nuAno;

    @Column(nullable = true, length = 50)
    @Setter
    private String chaveProcesso;

    @Column(nullable = false)
    @Setter
    private String descricao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cdAssunto", nullable = false)
    @Setter
    private Assunto assunto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cdInteressado", nullable = false)
    @Setter
    private Interessado interessado;
}
