package br.com.rgm.processos.repositories;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.rgm.processos.entities.Interessado;
import br.com.rgm.processos.utils.Ativo;

@DataJpaTest
class InteressadoRepositoryTest {

	@Autowired
	InteressadoRepository sut;

	@AfterEach
	void tearDown() {
		sut.deleteAll();
	}

	@BeforeEach
	void setup() {
		Interessado interessado = new Interessado();
		interessado.setDtNascimento(new Date());
		interessado.setFlAtivo(Ativo.SIM.value());
		interessado.setNmInteressado("numero");
		interessado.setNuIdentificacao("indentificacao");

		sut.save(interessado);
	}

	@Test
	void deveBuscarTodosOsAssuntosComOFlAtivoInformado() {

		// given
		// when
		List<Interessado> allActive = sut.findAllActive(Ativo.SIM.value());

		// then
		assertThat(allActive).asList().hasAtLeastOneElementOfType(Interessado.class);
	}
	
	@Test
	void deveBuscarPeloNumeroDeIndentificacao() {
		// given
		// when
		Interessado result = sut.findBynuIdentificacao("indentificacao").get();
		// then
		assertThat(result).isOfAnyClassIn(Interessado.class);
		assertThat(result.getNmInteressado()).isEqualTo("numero");
	}
}
