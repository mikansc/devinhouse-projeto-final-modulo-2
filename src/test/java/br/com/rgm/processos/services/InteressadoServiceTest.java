package br.com.rgm.processos.services;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyChar;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.rgm.processos.entities.Interessado;
import br.com.rgm.processos.repositories.InteressadoRepository;
import br.com.rgm.processos.services.exceptions.ObjectNotFoundException;
import br.com.rgm.processos.utils.Ativo;

@ExtendWith(SpringExtension.class)
class InteressadoServiceTest {

	@Mock
	private InteressadoRepository interessadoRepository;

	@InjectMocks
	private InteressadoService sut;

	@Test
	void deveRetornarTodosOsInteressadosComFlAtivo() {
		// given
		when(interessadoRepository.findAllActive(anyChar())).thenReturn(List.of(mock(Interessado.class)));
		
		// when
		sut.buscarTodosInteressados(anyChar());
		
		// then
		verify(interessadoRepository).findAllActive(anyChar());
	}
	
	@Test
	void deveRetornarTodosOsInteressadosSemNenhumQueryDeAtivo() {
		// given
		when(interessadoRepository.findAll()).thenReturn(List.of(mock(Interessado.class)));

		// when
		sut.buscarTodosInteressados(null);
		
		// then
		verify(interessadoRepository).findAll();
	}

	@Test
	void deveBuscarUmInteressadoPorID() {
		// given
		int ID = 1;
		when(interessadoRepository.findById(anyInt())).thenReturn(Optional.of(mock(Interessado.class)));

		// when
		sut.buscarPorId(ID);

		// given
		verify(interessadoRepository).findById(ID);
	}

	@Test
	void deveEstourarExcecaoSeNenhumInteressadoComIDInformadoForEncontrado() {
		// given
		int ID = 2;

		// then
		assertThatThrownBy(() -> sut.buscarPorId(ID))
			.isInstanceOf(ObjectNotFoundException.class)
			.hasMessageContaining("Nenhum interessado encontrado com o ID informado");
	}

	@Test
	void deveBuscarUmInteressadoPorNumeroDeIndentificacao() {
		// given
		when(interessadoRepository.findBynuIdentificacao(anyString())).thenReturn(Optional.of(mock(Interessado.class)));

		// when
		sut.buscarPorDocumento(anyString());

		// given
		verify(interessadoRepository).findBynuIdentificacao(anyString());
	}

	@Test
	void deveEstourarExcecaoSeNenhumInteressadoComDocumentoInformadoForEncontrado() {
		// given
		// then
		assertThatThrownBy(() -> sut.buscarPorDocumento(anyString()))
			.isInstanceOf(ObjectNotFoundException.class)
			.hasMessageContaining("Nenhum interessado encontrado com o número de identificação informado");
	}
	@Test
	void deveCadastrarInteressadoComFlAtivoPorPadrão() {
        //given
        Interessado interessado = new Interessado();

        //when
        sut.cadastrarInteressado(interessado);

        //then
        ArgumentCaptor<Interessado> interessadoArgumentCaptor = ArgumentCaptor.forClass(Interessado.class);
        verify(interessadoRepository).save(interessadoArgumentCaptor.capture());

        Interessado capturedInteressado = interessadoArgumentCaptor.getValue();
        assertThat(capturedInteressado).isEqualTo(interessado);
        assertThat(capturedInteressado.getFlAtivo()).isEqualTo(Ativo.SIM.value());
	}

	@Test
	void deveAtualizarInformacoesDoInteressado() {
		//given
        Interessado interessado = new Interessado(1,"Nome","Indentificacao",new Date(),'S');
        Interessado interessadoAtualizado = new Interessado(1,"NomeAtualizado","IndentificacaoAtualizado",new Date(),'S');
        when(interessadoRepository.findById(1)).thenReturn(Optional.of(interessado));

        //when
        sut.atualizarInteressado(1, interessadoAtualizado);

        //then
        ArgumentCaptor<Interessado> interessadoArgumentCaptor = ArgumentCaptor.forClass(Interessado.class);
        verify(interessadoRepository).save(interessadoArgumentCaptor.capture());
        
        Interessado capturedInteressado = interessadoArgumentCaptor.getValue();
        assertThat(capturedInteressado.getClass()).isEqualTo(interessadoAtualizado.getClass());
        assertThat(capturedInteressado.getNmInteressado()).isEqualTo("NomeAtualizado");
        assertThat(capturedInteressado.getNuIdentificacao()).isEqualTo("IndentificacaoAtualizado");
	}

	@Test
	void deveAlterarInteressadoAtivoParaInativo() {
		//given
        Interessado interessado = new Interessado(1,"Nome","Indentificacao",new Date(),'S');
        when(interessadoRepository.findById(1)).thenReturn(Optional.of(interessado));

        //when
        sut.alterarAtivoInteressado(1);

        //then
        assertThat(sut.buscarPorId(1).getFlAtivo()).isEqualTo(Ativo.NAO.value());
	}
	
	@Test
	void deveAlterarInteressadoInativoParaAtivo() {
		//given
		Interessado interessado = new Interessado(1,"Nome","Indentificacao",new Date(),'N');
        when(interessadoRepository.findById(1)).thenReturn(Optional.of(interessado));

        //when
        sut.alterarAtivoInteressado(1);

        //then
        assertThat(sut.buscarPorId(1).getFlAtivo()).isEqualTo(Ativo.SIM.value());
		
	}

}
