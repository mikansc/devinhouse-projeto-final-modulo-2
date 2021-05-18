package br.com.rgm.processos.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class AssuntoDTO {

	@Getter
	@Setter
	private Integer id;

	@Getter
	@Setter
	private String descricao;

	@Getter
	@Setter
	private Date dtCadastro;
}