package br.com.rgm.processos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class InteressadoDTO {

    private Integer id;

    @NotBlank(message = "Você precisa informar um nome")
    private String nmInteressado;

    @NotBlank(message = "Você precisa informar um número de documento")
    @CPF(message = "Você precisa informar um número de CPF válido")
    private String nuIdentificacao;

    @NotNull(message = "Você precisa informar uma data de nascimento válida")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dtNascimento;

}
