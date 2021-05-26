package br.com.rgm.processos.controllers;

import br.com.rgm.processos.dto.AssuntoDTO;
import br.com.rgm.processos.entities.Assunto;
import br.com.rgm.processos.services.AssuntoService;
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
@RequestMapping(path = "v1/assuntos", headers = "api-version=v1")
public class AssuntoController {

    @Autowired
    private AssuntoService assuntoService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AssuntoDTO>> buscarTodosAssunto(@RequestParam(required = false) Character ativo) {
        List<Assunto> listaAssunto = assuntoService.buscarTodosAssuntos(ativo);
        List<AssuntoDTO> listaAssuntoDTO = listaAssunto.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(listaAssuntoDTO);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AssuntoDTO> buscarAssunto(@PathVariable Integer id) {
        Assunto assunto = assuntoService.buscarAssunto(id);
        return ResponseEntity.ok(toDTO(assunto));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AssuntoDTO> cadastrarAssunto(@Valid @RequestBody AssuntoDTO assuntoDTO) {
        Assunto assunto = modelMapper.map(assuntoDTO, Assunto.class);
        AssuntoDTO responseBody = modelMapper.map(assuntoService.cadastrarAssunto(assunto), AssuntoDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @PutMapping(path = "/alterar-ativo/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> alterarAtivoAssunto(@PathVariable Integer id) {
        assuntoService.alterarAtivoAssunto(id);
        return ResponseEntity.noContent().build();
    }

    private AssuntoDTO toDTO(Assunto assunto) {
        return modelMapper.map(assunto, AssuntoDTO.class);
    }

}
