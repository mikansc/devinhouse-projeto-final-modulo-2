package br.com.rgm.processos.controllers;

import br.com.rgm.processos.dto.ProcessoDTOInput;
import br.com.rgm.processos.dto.ProcessoDTOOutput;
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
@RequestMapping(path = "/v1/processo", headers = "api-version=v1")
public class ProcessoController {

    @Autowired
    ProcessoService processoService;

    @Autowired
    ModelMapper modelMapper;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProcessoDTOOutput>> buscarProcessos(@RequestParam(name = "cd_interessado", required = false) Integer cdInteressado,
                                                                   @RequestParam(name = "cd_assunto", required = false) Integer cdAssunto) {

        List<ProcessoDTOOutput> foundObj;

        if (cdInteressado != null) {
            List<Processo> found = processoService.buscarPorInteressado(cdInteressado);
            foundObj = found.stream().map(this::toOutputDTO).collect(Collectors.toList());
        } else if (cdAssunto != null) {
            List<Processo> found = processoService.buscarPorAssunto(cdAssunto);
            foundObj = found.stream().map(this::toOutputDTO).collect(Collectors.toList());
        } else {
            List<Processo> found = processoService.buscarProcessos();
            foundObj = found.stream().map(this::toOutputDTO).collect(Collectors.toList());
        }

        return ResponseEntity.ok(foundObj);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProcessoDTOOutput> buscarPorId(@PathVariable Integer id) {
        Processo processo = processoService.buscarPorId(id);
        return ResponseEntity.ok(toOutputDTO(processo));
    }

    @GetMapping(path = "/chave-processo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProcessoDTOOutput> buscarPorChaveProcesso(@RequestParam(name = "valor") String chaveProcesso) {
        Processo processo = processoService.buscarPorChaveProcesso(chaveProcesso);
        return ResponseEntity.ok(toOutputDTO(processo));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProcessoDTOOutput> cadastrarProcesso(@Valid @RequestBody ProcessoDTOInput processo) {

        ProcessoDTOOutput createdObj = processoService.cadastrarProcesso(processo);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdObj);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> atualizarProcessoPorId(@PathVariable Integer id, @RequestBody ProcessoDTOInput processoDTOInput) {
        processoService.atualizarPorId(id, processoDTOInput);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> apagarProcessoPorId(@PathVariable Integer id) {
        processoService.apagarProcessoPorId(id);
        return ResponseEntity.noContent().build();
    }

    private ProcessoDTOOutput toOutputDTO(Processo processo) {
        return modelMapper.map(processo, ProcessoDTOOutput.class);
    }

    private Processo toProcesso(ProcessoDTOInput processoDTOInput) {
        return modelMapper.map(processoDTOInput, Processo.class);
    }
}
