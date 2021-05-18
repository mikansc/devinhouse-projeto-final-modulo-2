package br.com.rgm.processos.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rgm.processos.dto.AssuntoDTO;
import br.com.rgm.processos.entities.Assunto;
import br.com.rgm.processos.services.AssuntoService;

@RestController
@RequestMapping(path="v1/assunto", headers= "api-version=v1")
public class AssuntoController {

	@Autowired
	private AssuntoService assuntoService;
	
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping(consumes="application/json")
	public ResponseEntity<AssuntoDTO> cadastrarAssunto(@RequestBody AssuntoDTO assuntoDTO) {
		Assunto assunto = toAssunto(assuntoDTO);
		AssuntoDTO responseBody = toDTO(assuntoService.cadastrarAssunto(assunto));
		return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
	}
	
	@GetMapping(path="/{id}", produces="application/json")
	public ResponseEntity<AssuntoDTO> buscarAssunto(@PathVariable Integer id) {
		Assunto assunto = assuntoService.buscarAssunto(id);
		return ResponseEntity.ok(toDTO(assunto));
	}
	
	@GetMapping(produces="application/json")
	public ResponseEntity<List<AssuntoDTO>> buscarTodosAssunto() {
		List<Assunto> listaAssunto = assuntoService.buscarTodosAssuntos();
		List<AssuntoDTO> listaAssuntoDTO = listaAssunto.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
		return ResponseEntity.ok(listaAssuntoDTO);
	}
	
	private AssuntoDTO toDTO(Assunto assunto) {
		return modelMapper.map(assunto, AssuntoDTO.class);
	}
	
	private Assunto toAssunto(AssuntoDTO assuntoDTO) {
		return modelMapper.map(assuntoDTO, Assunto.class);
	}

}
