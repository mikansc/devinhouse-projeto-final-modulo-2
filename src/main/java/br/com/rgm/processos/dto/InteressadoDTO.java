package br.com.rgm.processos.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class InteressadoDTO {

	private Integer id;
	private String nmInteressado;
	private String nuIdentificacao;
	private Date dtNascimento;
}
