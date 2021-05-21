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

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/v1/processo", headers="api-version=v1")
public class ProcessoController {

    @Autowired
    ProcessoService processoService;

    @Autowired
    ModelMapper modelMapper;



    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProcessoDTO>> buscarProcessos(@RequestParam(name = "cd_interessado", required = false) Integer cdInteressado,
                                                             @RequestParam(name = "cd_assunto", required = false) Integer cdAssunto) {

        List<ProcessoDTO> foundObj;

        if (cdInteressado != null) {
            List<Processo> found = processoService.buscarPorInteressado(cdInteressado);
            foundObj = found.stream().map(this::toDTO).collect(Collectors.toList());
        } else if (cdAssunto != null) {
            List<Processo> found = processoService.buscarPorAssunto(cdAssunto);
            foundObj = found.stream().map(this::toDTO).collect(Collectors.toList());
        } else {
            List<Processo> found = processoService.buscarProcessos();
            foundObj = found.stream().map(this::toDTO).collect(Collectors.toList());
        }

        return ResponseEntity.ok(foundObj);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProcessoDTO> cadastrarProcesso(@Valid @RequestBody ProcessoDTO processo) {

        ProcessoDTO createdObj = processoService.cadastrarProcesso(processo);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdObj);
    }
    
    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProcessoDTO> buscarPorId(@PathVariable Integer id) {
    	Processo processo = processoService.buscarPorId(id);
    	return ResponseEntity.ok(toDTO(processo));
    }

    @GetMapping(path="/chave-processo",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProcessoDTO> buscarPorChaveProcesso(@RequestParam(name= "valor") String chaveProcesso) {
    	Processo processo = processoService.buscarPorChaveProcesso(chaveProcesso);
    	return ResponseEntity.ok(toDTO(processo));
    }
    
    @DeleteMapping(path="/{id}")
    public ResponseEntity<?> apagarProcessoPorId(@PathVariable Integer id) {
    	processoService.apagarProcessoPorId(id);
    	return ResponseEntity.noContent().build();
    }
    
    @PutMapping(path="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> atualizarProcessoPorId(@PathVariable Integer id, @RequestBody ProcessoDTO processoDTO) {
    	processoService.atualizarPorId(id, toProcesso(processoDTO));
    	return ResponseEntity.noContent().build();
    }

    private ProcessoDTO toDTO(Processo processo) {
        return modelMapper.map(processo, ProcessoDTO.class);
    }

    private Processo toProcesso(ProcessoDTO processoDTO) {
        return modelMapper.map(processoDTO, Processo.class);
    }
}
