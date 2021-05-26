package br.com.rgm.processos.dto;

import br.com.rgm.processos.entities.Assunto;
import br.com.rgm.processos.entities.Interessado;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProcessoDTOOutput {

    private Integer id;
    private Integer nuProcesso;
    private String sgOrgaoSetor;
    private String nuAno;
    private String chaveProcesso;
    private String descricao;
    private Assunto assunto;
    private Interessado interessado;

}
