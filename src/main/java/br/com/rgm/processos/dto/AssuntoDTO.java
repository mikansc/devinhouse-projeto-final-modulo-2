package br.com.rgm.processos.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AssuntoDTO {

	private Integer id;
	private String descricao;
	private Date dtCadastro;
}