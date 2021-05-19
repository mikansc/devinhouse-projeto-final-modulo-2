package br.com.rgm.processos.services;

import br.com.rgm.processos.entities.Assunto;
import br.com.rgm.processos.entities.Interessado;
import br.com.rgm.processos.entities.Processo;
import br.com.rgm.processos.repositories.ProcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessoService {

    @Autowired
    ProcessoRepository processoRepository;

    @Autowired
    InteressadoService interessadoService;

    @Autowired
    AssuntoService assuntoService;

    public List<Processo> buscarProcessos() {
        return processoRepository.findAll();
    }

    public List<Processo> buscarPorInteressado(Integer cdInteressado) {
        Interessado interessado = interessadoService.buscarInteressado(cdInteressado);
        List<Processo> response = processoRepository.findByInteressado(interessado);
        return response;
    }

    public List<Processo> buscarPorAssunto(Integer cdAssunto) {
        Assunto assunto = assuntoService.buscarAssunto(cdAssunto);
        List<Processo> response = processoRepository.findByAssunto(assunto);
        return response;
    }

}
