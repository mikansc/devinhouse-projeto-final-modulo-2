package br.com.rgm.processos.services;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.rgm.processos.entities.Interessado;
import br.com.rgm.processos.repositories.InteressadoRepository;

@ExtendWith(MockitoExtension.class)
class InteressadoServiceTest {

	@Mock
	private InteressadoRepository interessadoRepository;
	
	@InjectMocks
	private InteressadoService sut;
	
	@Test
	void testBuscarTodosInteressados() {
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
	void testBuscarPorDocumento() {
	}

	@Test
	void testCadastrarInteressado() {
	}

	@Test
	void testAtualizarInteressado() {
	}

	@Test
	void testAlterarAtivoInteressado() {
	}

}
