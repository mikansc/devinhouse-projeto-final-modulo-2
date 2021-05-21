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
	private AssuntoRepository assuntoRepepository;

	public Assunto cadastrarAssunto(Assunto novoAssunto) {
		novoAssunto.setFlAtivo(Ativo.SIM.getValor());
		return assuntoRepepository.save(novoAssunto);
	}
	
	public Assunto buscarAssunto(Integer id) {
		return assuntoRepepository.findById(id)
				.orElseThrow(()-> new ObjectNotFoundException("Nenhum assunto encontrado com o ID informado"));
	}
	
	public List<Assunto> buscarTodosAssuntos() {
		return assuntoRepepository.findAll();
	}

}
