package br.com.rgm.processos.repositories;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.rgm.processos.entities.Assunto;
import br.com.rgm.processos.entities.Interessado;
import br.com.rgm.processos.entities.Processo;

@DataJpaTest
class ProcessoRepositoryTest {

	@Autowired
	ProcessoRepository sut;

	@AfterEach
	void tearDown() {
		sut.deleteAll();
	}

	@Test
	void testFindByChaveProcesso() {
		// given
		// when
		Processo result = sut.findByChaveProcesso("SOFT 1/2021").get();

		// then
		assertThat(result).isOfAnyClassIn(Processo.class);
		assertThat(result.getDescricao()).isEqualTo("Processo");
	}

	@Test
	void testFindByInteressado() {
		// given
		Interessado interessado = new Interessado(2,"Maria Joaquina", "217.787.750-47", new Date(), 'S');
		// when
		List<Processo> allResults = sut.findByInteressado(interessado);

		// then
		assertThat(allResults).asList().hasAtLeastOneElementOfType(Processo.class);
	}

	@Test
	void testFindByAssunto() {
		// given
		Assunto assunto = new Assunto(1, "Autorização para Corte de Árvores - Área Pública", new Date(), 'S');
		// when
		List<Processo> allResults = sut.findByAssunto(assunto);

		// then
		assertThat(allResults).asList().hasAtLeastOneElementOfType(Processo.class);
	}

}
