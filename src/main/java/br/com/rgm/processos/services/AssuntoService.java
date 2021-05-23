package br.com.rgm.processos.services;

import java.util.List;

import br.com.rgm.processos.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rgm.processos.entities.Assunto;
import br.com.rgm.processos.repositories.AssuntoRepository;
import br.com.rgm.processos.utils.Ativo;

@Service
public class AssuntoService {

	@Autowired
	private AssuntoRepository assuntoRepository;

	public Assunto cadastrarAssunto(Assunto novoAssunto) {
		novoAssunto.setFlAtivo(Ativo.SIM.value());
		return assuntoRepository.save(novoAssunto);
	}
	
	public Assunto buscarAssunto(Integer id) {
		return assuntoRepository.findById(id)
				.orElseThrow(()-> new ObjectNotFoundException("Nenhum assunto encontrado com o ID informado"));
	}
	
	public List<Assunto> buscarTodosAssuntos(Character ativo) {
//		return assuntoRepository.findAll();
		if(ativo != null) {
			return assuntoRepository.findAllActive(ativo);
		} else {
			return assuntoRepository.findAll();
		}
	}
	
	public void alterarAtivoAssunto(Integer id) {
		Assunto atual = assuntoRepository.findById(id).get();
		if(atual.getFlAtivo() == Ativo.SIM.value()) {
			atual.setFlAtivo(Ativo.NAO.value());			
		} else {			
			atual.setFlAtivo(Ativo.SIM.value());			
		}
		assuntoRepository.save(atual);
	}

}
