package br.com.rgm.processos.services;

import br.com.rgm.processos.dto.ProcessoDTOInput;
import br.com.rgm.processos.dto.ProcessoDTOOutput;
import br.com.rgm.processos.entities.Assunto;
import br.com.rgm.processos.entities.Interessado;
import br.com.rgm.processos.entities.Processo;
import br.com.rgm.processos.repositories.ProcessoRepository;
import br.com.rgm.processos.services.exceptions.ObjectNotFoundException;
import br.com.rgm.processos.utils.Ativo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
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

    public Processo buscarPorId(Integer id) {
        return processoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Nenhum processo encontrado com o ID informado"));
    }

    public Processo buscarPorChaveProcesso(String chaveProcesso) {
        return processoRepository.findByChaveProcesso(chaveProcesso)
                .orElseThrow(() -> new ObjectNotFoundException("Nenhum processo encontrado com a chave de processo informada"));
    }

    public ProcessoDTOOutput cadastrarProcesso(ProcessoDTOInput createdProcessoDTO) {

        Assunto assunto = assuntoService.buscarAssunto(createdProcessoDTO.getCdAssunto());
        if (assunto.getFlAtivo() == Ativo.NAO.value()) {
            throw new ObjectNotFoundException("Assunto inativo, não é possivel cadastrar o Processo!");
        }

        Interessado interessado = interessadoService.buscarInteressado(createdProcessoDTO.getCdInteressado());
        if (interessado.getFlAtivo() == Ativo.NAO.value()) {
            throw new ObjectNotFoundException("Interessado inativo, não é possivel cadastrar o Processo!");
        }

        Processo createdProcesso = modelMapper.map(createdProcessoDTO, Processo.class);
        createdProcesso.setAssunto(assunto);
        createdProcesso.setInteressado(interessado);

        Processo processoToSave = processoRepository.save(createdProcesso);

        return modelMapper.map(processoToSave, ProcessoDTOOutput.class);
    }

    public void atualizarPorId(Integer id, ProcessoDTOInput processoAtualizadoDTO) {

        Interessado interessado = interessadoService.buscarInteressado(processoAtualizadoDTO.getCdInteressado());
        Assunto assunto = assuntoService.buscarAssunto(processoAtualizadoDTO.getCdAssunto());
        Processo processoExistente = this.buscarPorId(id);

        Processo processoAtualizado = modelMapper.map(processoAtualizadoDTO, Processo.class);
        processoAtualizado.setAssunto(assunto);
        processoAtualizado.setInteressado(interessado);

        // Atualiza chave processo com base em possíveis atualizações no Ano e Sigla do órgão.
        processoExistente.setChaveProcesso(String.format("%s %d/%s",
                processoAtualizado.getSgOrgaoSetor().toUpperCase(),
                processoExistente.getId(),
                processoAtualizado.getNuAno()));

        BeanUtils.copyProperties(processoAtualizado, processoExistente, "id", "chaveProcesso", "nuProcesso");

        processoRepository.save(processoExistente);

    }

    public void apagarProcessoPorId(Integer id) {
        processoRepository.deleteById(id);
    }

}
