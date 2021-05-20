package br.com.rgm.processos.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Processo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Integer id;

    @Column(nullable = true)
    @Getter
    @Setter
    private Integer nuProcesso;

    @Column(nullable = false, length = 4)
    @Getter
    @Setter
    private String sgOrgaoSetor;

    @Column(nullable = false, length = 4)
    @Getter
    @Setter
    private String nuAno;

    @Column(nullable = true, length = 50)
    @Getter
    @Setter
    private String chaveProcesso;

    @Column(nullable = false)
    @Getter
    @Setter
    private String descricao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cdAssunto", nullable = false)
    @Getter
    @Setter
    private Assunto assunto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cdInteressado", nullable = false)
    @Getter
    @Setter
    private Interessado interessado;
}
