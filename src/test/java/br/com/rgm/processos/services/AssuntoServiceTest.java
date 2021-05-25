package br.com.rgm.processos.services;

import br.com.rgm.processos.entities.Assunto;
import br.com.rgm.processos.repositories.AssuntoRepository;
import br.com.rgm.processos.services.exceptions.ObjectNotFoundException;
import br.com.rgm.processos.utils.Ativo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

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
        assunto.setDtCadastro(new Date());

        //when
        sut.cadastrarAssunto(assunto);

        //then
        ArgumentCaptor<Assunto> assuntoArgumentCaptor = ArgumentCaptor.forClass(Assunto.class);
        verify(assuntoRepository).save(assuntoArgumentCaptor.capture());

        Assunto capturedAssunto = assuntoArgumentCaptor.getValue();
        assertThat(capturedAssunto).isEqualTo(assunto);
        assertThat(capturedAssunto.getFlAtivo()).isEqualTo(Ativo.SIM.value());
        assertThat(capturedAssunto.getDescricao()).isEqualTo(assunto.getDescricao());
        assertThat(capturedAssunto.getDtCadastro()).isEqualTo(assunto.getDtCadastro());

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
        sut.buscarTodosAssuntos(null);

        // then
        verify(assuntoRepository).findAll();

    }

    @Test
    void deveAlterarFlAtivoDoAssuntoParaInativo() {
    	//given
    	Assunto assunto = new Assunto(1,"descricao",new Date(),Ativo.SIM.value());
    	when(assuntoRepository.findById(eq(1))).thenReturn(Optional.of(assunto));
    	//when
        sut.alterarAtivoAssunto(1);
    	//then
        assertThat(sut.buscarAssunto(1).getFlAtivo()).isEqualTo(Ativo.NAO.value());
    }
    
    @Test
    void deveAlterarFlAtivoDoAssuntoParaAtivo() {
    	//given
    	Assunto assunto = new Assunto(1,"descricao",new Date(),Ativo.NAO.value());
    	when(assuntoRepository.findById(eq(1))).thenReturn(Optional.of(assunto));
    	//when
        sut.alterarAtivoAssunto(1);
    	//then
        assertThat(sut.buscarAssunto(1).getFlAtivo()).isEqualTo(Ativo.SIM.value());
    }
}