package br.com.rgm.processos.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class InteressadoDTO {

	@Getter
	@Setter
	private Integer id;

	@Getter
	@Setter
	private String nmInteressado;

	@Getter
	@Setter
	private String nuIdentificacao;

	@Getter
	@Setter
	private Date dtNascimento;

}
