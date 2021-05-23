package br.com.rgm.processos.controllers;

import br.com.rgm.processos.dto.InteressadoDTO;
import br.com.rgm.processos.entities.Interessado;
import br.com.rgm.processos.services.InteressadoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "v1/interessados", headers = "api-version=v1")
public class InteressadoController {

    @Autowired
    private InteressadoService interessadoService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InteressadoDTO> buscarInteressado(@PathVariable Integer id) {
        Interessado interessado = interessadoService.buscarInteressado(id);
        return ResponseEntity.ok(toDTO(interessado));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InteressadoDTO> buscarInteressadoComNumeroIndentificação(@RequestParam(name = "numero_indentificacao") String indentificacao) {
        Interessado interessado = interessadoService.buscarInteressado(indentificacao);
        return ResponseEntity.ok(toDTO(interessado));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InteressadoDTO> cadastrarInteressado(@Valid @RequestBody InteressadoDTO interessadoDTO) {
        Interessado interessado = toInteressado(interessadoDTO);
        InteressadoDTO responseBody = toDTO(interessadoService.cadastrarInteressado(interessado));
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);

    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> atualizarInteressado(@PathVariable Integer id, @RequestBody InteressadoDTO interessadoDTO) {
        interessadoService.atualizarInteressado(id, toInteressado(interessadoDTO));
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/alterar-ativo/{id}")
    public ResponseEntity<?> alterarAtivoInteressado(@PathVariable Integer id) {
        interessadoService.alterarAtivoInteressado(id);
        return ResponseEntity.noContent().build();
    }

    private InteressadoDTO toDTO(Interessado interessado) {
        return modelMapper.map(interessado, InteressadoDTO.class);
    }

    private Interessado toInteressado(InteressadoDTO interessadoDTO) {
        return modelMapper.map(interessadoDTO, Interessado.class);
    }

}
