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

    @Column(nullable = false, unique = true)
    @Setter
    private Integer nuProcesso;

    @Column(nullable = false, length = 4)
    @Setter
    private String sgOrgaoSetor;

    @Column(nullable = false, length = 4)
    @Setter
    private String nuAno;

    @Column(nullable = false, unique = true, length = 50)
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

    @PrePersist
    public void validaCamposAutoGerados() {
        if (nuProcesso == null) {
            nuProcesso = 0;
        }
        chaveProcesso = String.format("%s %d/%s", this.sgOrgaoSetor, this.nuProcesso, this.nuAno);
    }

    @PostPersist
    public void geraVaLoresCamposAutoGerados() {
        nuProcesso = this.id;
        chaveProcesso = String.format("%s %d/%s", this.sgOrgaoSetor, this.nuProcesso, this.nuAno);
    }
}
