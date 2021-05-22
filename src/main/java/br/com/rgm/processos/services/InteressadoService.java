package br.com.rgm.processos.services;

import br.com.rgm.processos.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rgm.processos.entities.Assunto;
import br.com.rgm.processos.entities.Interessado;
import br.com.rgm.processos.repositories.InteressadoRepository;
import br.com.rgm.processos.utils.Ativo;

@Service
public class InteressadoService {

	@Autowired
	private InteressadoRepository interessadoRepository;

	public Interessado cadastrarInteressado(Interessado interessado) {
		interessado.setFlAtivo(Ativo.SIM.value());
		return interessadoRepository.save(interessado);
	}

	public Interessado buscarInteressado(Integer id) {
		return interessadoRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Nenhum interessado encontrado com o ID informado"));
	}

	public Interessado buscarInteressado(String nuIdentificacao) {
		return interessadoRepository.findBynuIdentificacao(nuIdentificacao)
				.orElseThrow(() -> new ObjectNotFoundException(
						"Nenhum interessado encontrado com o número de identificação informado"));
	}

	public void alterarAtivoInteressado(Integer id) {
		Interessado atual = interessadoRepository.findById(id).get();
		if (atual.getFlAtivo() == Ativo.SIM.value()) {
			atual.setFlAtivo(Ativo.NAO.value());
		} else {
			atual.setFlAtivo(Ativo.SIM.value());
		}
		interessadoRepository.save(atual);
	}

	public void atualizarInteressado(Integer id, Interessado novoInteressado) {
		Interessado atual = this.buscarInteressado(id);
		BeanUtils.copyProperties(novoInteressado, atual, "id","flAtivo");
		interessadoRepository.save(atual);
		
	}

}
