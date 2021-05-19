package br.com.rgm.processos.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.rgm.processos.dto.InteressadoDTO;
import br.com.rgm.processos.entities.Interessado;
import br.com.rgm.processos.services.InteressadoService;

@RestController
@RequestMapping(path="v1/interessado", headers="api-version=v1")
public class InteressadoController {
	
	@Autowired
	private InteressadoService interessadoService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping(consumes="application/json")
	public ResponseEntity<InteressadoDTO> cadastrarInteressado(@RequestBody InteressadoDTO interessadoDTO) {
		Interessado interessado = toInteressado(interessadoDTO);
		InteressadoDTO responseBody = toDTO(interessadoService.cadastrarInteressado(interessado));
		return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
		
	}
	
	@GetMapping(path="/{id}", produces="application/json")
	public ResponseEntity<InteressadoDTO> buscarInteressado(@PathVariable Integer id) {
		Interessado interessado = interessadoService.buscarInteressado(id);
		return ResponseEntity.ok(toDTO(interessado));
	}
	
	@GetMapping(produces="application/json")
	public ResponseEntity<InteressadoDTO> buscarInteressadoComNumeroIndentificação(@RequestParam String indentificacao) {
		Interessado interessado = interessadoService.buscarInteressado(indentificacao);
		return ResponseEntity.ok(toDTO(interessado));
	}
	
	
	private InteressadoDTO toDTO(Interessado interessado) {
		return modelMapper.map(interessado, InteressadoDTO.class);
	}
	
	private Interessado toInteressado(InteressadoDTO interessadoDTO) {
		return modelMapper.map(interessadoDTO, Interessado.class);
	}

}
