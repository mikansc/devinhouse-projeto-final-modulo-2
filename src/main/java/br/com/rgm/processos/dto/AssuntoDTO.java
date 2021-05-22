package br.com.rgm.processos.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class AssuntoDTO {

	private Integer id;

	@NotNull(message = "Uma descrição para o assunto não foi informada")
	private String descricao;

	@NotNull(message = "Uma data de cadastro não foi informada")
	private Date dtCadastro;
}