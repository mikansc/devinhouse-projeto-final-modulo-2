package br.com.rgm.processos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class ProcessoDTOInput {

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
    @NotNull(message = "Você precisa informar um cod. Assunto")
    private Integer cdAssunto;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotNull(message = "Você precisa informar um cod. Interessado")
    private Integer cdInteressado;

}
