package br.com.rgm.processos.repositories;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
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
		Interessado result = sut.findBynuIdentificacao("217.787.750-47").get();
		// then
		assertThat(result).isOfAnyClassIn(Interessado.class);
		assertThat(result.getNmInteressado()).isEqualTo("Maria Joaquina");
	}
}
