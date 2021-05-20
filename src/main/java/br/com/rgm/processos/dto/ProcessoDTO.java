package br.com.rgm.processos.dto;

import br.com.rgm.processos.entities.Assunto;
import br.com.rgm.processos.entities.Interessado;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProcessoDTO {

    private Integer id;
    private Integer nuProcesso;
    private String sgOrgaoSetor;
    private String nuAno;
    private String chaveProcesso;
    private String descricao;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer cdAssunto;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer cdInteressado;

    private Assunto assunto;
    private Interessado interessado;
}
