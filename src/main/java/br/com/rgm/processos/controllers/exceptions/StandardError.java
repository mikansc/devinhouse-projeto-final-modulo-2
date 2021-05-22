package br.com.rgm.processos.controllers.exceptions;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StandardError {

	private Integer statusCode;
	private String message;
	private Long timestamp;

	private List<Campo> fields;

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Campo {
		private String name;
		private String message;
	}
}
