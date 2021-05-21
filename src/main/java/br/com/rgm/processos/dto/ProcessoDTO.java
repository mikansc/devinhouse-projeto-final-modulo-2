package br.com.rgm.processos.dto;

import br.com.rgm.processos.entities.Assunto;
import br.com.rgm.processos.entities.Interessado;

import javax.validation.constraints.NotBlank;

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
    
    @NotBlank(message = "Você precisa informar uma sigla de um orgão")
    private String sgOrgaoSetor;
    
    @NotBlank(message = "Você precisa informar um ano")
    private String nuAno;
    private String chaveProcesso;
    
    @NotBlank(message = "Você precisa informar uma descrição")
    private String descricao;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank(message = "Você precisa informar um cod. Assunto")
    private Integer cdAssunto;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank(message = "Você precisa informar um cod. Interessado")
    private Integer cdInteressado;

    private Assunto assunto;
    private Interessado interessado;
}
