package br.com.rgm.processos.services;

import br.com.rgm.processos.dto.ProcessoDTO;
import br.com.rgm.processos.entities.Assunto;
import br.com.rgm.processos.entities.Interessado;
import br.com.rgm.processos.entities.Processo;
import br.com.rgm.processos.repositories.ProcessoRepository;
import br.com.rgm.processos.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
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

    @Autowired
    ModelMapper modelMapper;

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

    public ProcessoDTO cadastrarProcesso(ProcessoDTO processoObj) {

        Interessado interessado = interessadoService.buscarInteressado(processoObj.getCdInteressado());
        Assunto assunto = assuntoService.buscarAssunto(processoObj.getCdAssunto());

        processoObj.setAssunto(assunto);
        processoObj.setInteressado(interessado);

        Processo temp = processoRepository.save(modelMapper.map(processoObj, Processo.class));

        temp.setNuProcesso(temp.getId());
        temp.setChaveProcesso(String.format("%s %d/%s",
                temp.getSgOrgaoSetor(),
                temp.getId(),
                temp.getNuAno()));

        Processo result = processoRepository.save(temp);

        return modelMapper.map(result, ProcessoDTO.class);
    }

    public Processo buscarPorId(Integer id) {
      return processoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Nenhum processo encontrado com o ID informado"));  	
    }

    public Processo buscarPorChaveProcesso(String chaveProcesso) {
    	return processoRepository.findByChaveProcesso(chaveProcesso);
    }

    public void apagarProcessoPorId(Integer id) {
    	processoRepository.deleteById(id);
    }

    public void atualizarPorId(Integer id, Processo novoProcesso) {
//    	if(processoRepository.existsById(id)) {
//    		processoRepository.save(novoProcesso);    		
//    	}
    }

}
