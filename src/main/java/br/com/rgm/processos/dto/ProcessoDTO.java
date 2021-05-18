package br.com.rgm.processos.dto;

import br.com.rgm.processos.entities.Assunto;
import br.com.rgm.processos.entities.Interessado;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class ProcessoDTO {
	@Getter
	private Integer id;

	@Getter
	@Setter
	private Integer nuProcesso;

	@Getter
	@Setter
	private String sgOrgaoSetor;

	@Getter
	@Setter
	private String nuAno;

	@Getter
	@Setter
	private String chaveProcesso;

	@Getter
	@Setter
	private String descricao;

	@Getter
	@Setter
	private Assunto assunto;

	@Getter
	@Setter
	private Interessado interessado;
}
