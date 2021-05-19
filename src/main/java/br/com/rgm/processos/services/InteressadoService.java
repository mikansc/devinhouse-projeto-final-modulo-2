package br.com.rgm.processos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rgm.processos.entities.Interessado;
import br.com.rgm.processos.repositories.InteressadoRepository;
import br.com.rgm.processos.utils.Ativo;

@Service
public class InteressadoService {

	@Autowired
	private InteressadoRepository interessadoRepository;
	
	public Interessado cadastrarInteressado(Interessado interessado) {
		interessado.setFlAtivo(Ativo.SIM.getValor());
		return interessadoRepository.save(interessado);
	}
	
	public Interessado buscarInteressado(Integer id) {
		return interessadoRepository.findById(id).get();
	}
	
	public Interessado buscarInteressado(String nuIdentificacao) {
		return interessadoRepository.findBynuIdentificacao(nuIdentificacao);
	}
		
}
