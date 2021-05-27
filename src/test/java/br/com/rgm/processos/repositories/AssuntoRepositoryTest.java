package br.com.rgm.processos.repositories;

import br.com.rgm.processos.entities.Assunto;
import br.com.rgm.processos.utils.Ativo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class AssuntoRepositoryTest {

    @Autowired
    AssuntoRepository sut;

    @AfterEach
    void tearDown() {
        sut.deleteAll();
    }

    @Test
    void deveBuscarTodosOsAssuntosComOFlAtivoInformado() {

        // given
        Assunto assunto = new Assunto();
        assunto.setDescricao("any string");
        assunto.setDtCadastro(new Date());
        assunto.setFlAtivo(Ativo.SIM.value());

        sut.save(assunto);

        //when

        List<Assunto> allActive = sut.findAllActive('S');

        //then
        assertThat(allActive).asList().hasAtLeastOneElementOfType(Assunto.class);
    }
}