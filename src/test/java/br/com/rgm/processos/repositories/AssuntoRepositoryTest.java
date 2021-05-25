package br.com.rgm.processos.repositories;

import br.com.rgm.processos.entities.Assunto;
import br.com.rgm.processos.utils.Ativo;
import lombok.Data;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

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
        assunto.setDescricao(anyString());
        assunto.setDtCadastro(new Date());
        assunto.setFlAtivo(Ativo.SIM.value());

        sut.save(assunto);

        //when

        List<Assunto> allActive = sut.findAllActive('S');

        //then
        assertThat(allActive).asList().hasAtLeastOneElementOfType(Assunto.class);
    }
}