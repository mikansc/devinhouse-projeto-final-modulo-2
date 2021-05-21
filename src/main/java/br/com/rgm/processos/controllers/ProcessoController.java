package br.com.rgm.processos.controllers;

import br.com.rgm.processos.dto.ProcessoDTO;
import br.com.rgm.processos.entities.Processo;
import br.com.rgm.processos.services.ProcessoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/v1/processo")
public class ProcessoController {

    @Autowired
    ProcessoService processoService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProcessoDTO>> buscarProcessos(@RequestParam(name = "cd_interessado", required = false) Integer cdInteressado,
                                                             @RequestParam(name = "cd_assunto", required = false) Integer cdAssunto) {

        List<ProcessoDTO> response;

        if (cdInteressado != null) {
            List<Processo> found = processoService.buscarPorInteressado(cdInteressado);
            response = found.stream().map(this::toDTO).collect(Collectors.toList());
        } else if (cdAssunto != null) {
            List<Processo> found = processoService.buscarPorAssunto(cdAssunto);
            response = found.stream().map(this::toDTO).collect(Collectors.toList());
        } else {
            List<Processo> found = processoService.buscarProcessos();
            response = found.stream().map(this::toDTO).collect(Collectors.toList());
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProcessoDTO> cadastrarProcesso(@RequestBody ProcessoDTO processo) {

        ProcessoDTO novoProcesso = processoService.cadastrarProcesso(processo);

        return ResponseEntity.status(HttpStatus.CREATED).body(novoProcesso);
    }

    private ProcessoDTO toDTO(Processo processo) {
        return modelMapper.map(processo, ProcessoDTO.class);
    }

    private Processo toProcesso(ProcessoDTO processoDTO) {
        return modelMapper.map(processoDTO, Processo.class);
    }
}
