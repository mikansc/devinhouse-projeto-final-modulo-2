package br.com.rgm.processos.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@NoArgsConstructor
public class InteressadoDTO {

    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    @NotBlank(message = "Você precisa informar um nome")
    private String nmInteressado;

    @Getter
    @Setter
    @NotBlank(message = "Você precisa informar um número de documento")
    @CPF(message = "Você precisa informar um número de CPF válido")
    private String nuIdentificacao;

    @Getter
    @Setter
    @NotBlank(message = "Você precisa informar uma data de nascimento válida")
    private Date dtNascimento;

}
