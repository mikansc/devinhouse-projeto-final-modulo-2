package br.com.rgm.processos.controllers.exceptions;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class StandardError {

	private Integer statusCode;
	private String message;
	private Long timestamp;
	@Setter
	private List<Campo> fields;

	@Getter
	@AllArgsConstructor
	public static class Campo {
		private String name;
		private String message;
	}
}
