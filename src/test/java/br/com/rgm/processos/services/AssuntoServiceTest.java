package br.com.rgm.processos.services;

import br.com.rgm.processos.entities.Assunto;
import br.com.rgm.processos.repositories.AssuntoRepository;
import br.com.rgm.processos.services.exceptions.ObjectNotFoundException;
import br.com.rgm.processos.utils.Ativo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssuntoServiceTest {


    @Mock
    private AssuntoRepository assuntoRepository;

    @InjectMocks
    private AssuntoService sut;

    @Test
    void deveCadastrarAssuntoComFlAtivoPorPadrao() {
        //given
        Assunto assunto = new Assunto();
        assunto.setDescricao(anyString());

        //when
        sut.cadastrarAssunto(assunto);

        //then
        ArgumentCaptor<Assunto> assuntoArgumentCaptor = ArgumentCaptor.forClass(Assunto.class);
        verify(assuntoRepository).save(assuntoArgumentCaptor.capture());

        Assunto capturedAssunto = assuntoArgumentCaptor.getValue();
        assertThat(capturedAssunto).isEqualTo(assunto);
        assertThat(capturedAssunto.getFlAtivo()).isEqualTo(Ativo.SIM.value());

    }

    @Test
    void deveBuscarUmAssuntoPeloSeuID() {
        // given
        int ID = 1;
        when(assuntoRepository.findById(anyInt())).thenReturn(Optional.of(mock(Assunto.class)));

        // when
        sut.buscarAssunto(ID);

        // then
        verify(assuntoRepository).findById(ID);

    }

    @Test
    void deveEstourarExcecaoSeNenhumAssuntoComIDInformadoForEncontrado() {
        // given
        int INTEIRO = 1;
        //then
        assertThatThrownBy(()-> sut.buscarAssunto(INTEIRO))
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessageContaining("Nenhum assunto encontrado com o ID informado");

    }

    @Test
    void deveRetornarTodosOsAssuntosComOFlAtivoInformado() {
        //given
        when(assuntoRepository.findAllActive(anyChar())).thenReturn(List.of(mock(Assunto.class)));
        // when
        sut.buscarTodosAssuntos(anyChar());

        // then
        verify(assuntoRepository).findAllActive(anyChar());

    }

    @Test
    void deveRetornarTodosOsAssuntosSeNenhumQueryDeAtivoForInformado() {
        //given
        when(assuntoRepository.findAll()).thenReturn(List.of(mock(Assunto.class)));

        // when
        List<Assunto> assuntoList = sut.buscarTodosAssuntos(null);

        // then
        assertThat(assuntoList).asList().hasOnlyElementsOfType(Assunto.class);
//        verify(assuntoRepository).findAll();

    }

    @Test
    @Disabled
    void alterarAtivoAssunto() {
    }
}