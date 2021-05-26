package br.com.rgm.processos.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import br.com.rgm.processos.dto.ProcessoDTOInput;
import br.com.rgm.processos.entities.Assunto;
import br.com.rgm.processos.entities.Interessado;
import br.com.rgm.processos.entities.Processo;
import br.com.rgm.processos.repositories.ProcessoRepository;
import br.com.rgm.processos.services.exceptions.InactiveObjectException;
import br.com.rgm.processos.services.exceptions.ObjectNotFoundException;
import br.com.rgm.processos.utils.Ativo;

@ExtendWith(MockitoExtension.class)
class ProcessoServiceTest {
	
	@Mock
	private ProcessoRepository processoRepository;
	
	@Mock
	private InteressadoService interessadoService;
	
	@Mock
	private AssuntoService assuntoService;
	
	@Mock
	private ModelMapper modelMapper;
	
	@InjectMocks
	private ProcessoService sut;

	@Test
	void deveRetornarTodosOsProcessos() {
		// given
		when(processoRepository.findAll()).thenReturn(List.of(mock(Processo.class)));
		
		// when
		sut.buscarProcessos();
		
		// then
		verify(processoRepository).findAll();
	}

	@Test
	void deveRetornarBuscarTodosOsProcessosPorInteressado() {
		// given
		Interessado interessado = new Interessado(1,"Nome","Indentificacao",new Date(),'S');
		when(interessadoService.buscarPorId(anyInt())).thenReturn(interessado);
		when(processoRepository.findByInteressado(interessado)).thenReturn(List.of(mock(Processo.class)));
		
		// when
		sut.buscarPorInteressado(anyInt());
		
		// then
		verify(processoRepository).findByInteressado(interessado);
	}

	@Test
	void deveRetornarBuscarTodosOsProcessosPorAssunto() {
		// given
		Assunto assunto = new Assunto(1,"descricao",new Date(),Ativo.SIM.value());
		when(assuntoService.buscarAssunto(anyInt())).thenReturn(assunto);
		when(processoRepository.findByAssunto(assunto)).thenReturn(List.of(mock(Processo.class)));
		
		// when
		sut.buscarPorAssunto(anyInt());
		
		// then
		verify(processoRepository).findByAssunto(assunto);
	}

	@Test
	void deveRetornarUmProcessoPeloSeuID() {
		// given
		when(processoRepository.findById(anyInt())).thenReturn(Optional.of(mock(Processo.class)));
		
		// when
		sut.buscarPorId(anyInt());
		
		// then
		verify(processoRepository).findById(anyInt());
	}
	
	@Test
	void deveEstourarExcecaoSeNenhumProcessoComIDInformadoForEncontrado() {
		//then
        assertThatThrownBy(()-> sut.buscarPorId(1))
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessageContaining("Nenhum processo encontrado com o ID informado");
		
	}

	@Test
	void deveRetornarUmProcessoPelaSuaChaveDeProcesso() {
		// given
		when(processoRepository.findByChaveProcesso(anyString())).thenReturn(Optional.of(mock(Processo.class)));
		
		// when
		sut.buscarPorChaveProcesso(anyString());
		
		// then
		verify(processoRepository).findByChaveProcesso(anyString());
	}
	
	@Test
	void deveEstourarExcecaoSeNenhumProcessoComChaveProcessoInformadoForEncontrado() {
		// then
		assertThatThrownBy(()-> sut.buscarPorChaveProcesso("chave"))
        .isInstanceOf(ObjectNotFoundException.class)
        .hasMessageContaining("Nenhum processo encontrado com a chave de processo informada");
	}

	@Test
	void deveCadastrarUmProcessoComInteressadoEAssuntoAtivos() {
		// given
		Assunto assunto = new Assunto(1,"descricao",new Date(),Ativo.SIM.value());
		Interessado interessado = new Interessado(1,"Nome","Indentificacao",new Date(),Ativo.SIM.value());
		ProcessoDTOInput processoDTOinput = new ProcessoDTOInput();
		processoDTOinput.setCdAssunto(1);
		processoDTOinput.setCdInteressado(1);
		processoDTOinput.setId(1);
		processoDTOinput.setNuProcesso(1);
		processoDTOinput.setDescricao("Processo Teste");
		processoDTOinput.setNuAno("2020");
		processoDTOinput.setSgOrgaoSetor("SOFT");
		processoDTOinput.setChaveProcesso("SOFT 1/2020");
		Processo processoResultado = new Processo(1,1,"SOFT","2020","SOFT 1/2020","Processo Teste",assunto,interessado);
		when(assuntoService.buscarAssunto(1)).thenReturn(assunto);
		when(interessadoService.buscarPorId(1)).thenReturn(interessado);
		when(modelMapper.map(processoDTOinput, Processo.class)).thenReturn(processoResultado);
		
		// when
		sut.cadastrarProcesso(processoDTOinput);
		
		// then
		ArgumentCaptor<Processo> processoArgumentCaptor = ArgumentCaptor.forClass(Processo.class);
        verify(processoRepository).save(processoArgumentCaptor.capture());
        
        Processo capturedProcesso = processoArgumentCaptor.getValue();
        assertThat(capturedProcesso.getClass()).isEqualTo(Processo.class);
        assertThat(capturedProcesso.getAssunto()).isEqualTo(assunto);
        assertThat(capturedProcesso.getInteressado()).isEqualTo(interessado);
	}
	
	@Test
	void deveEstourarExcecaoSeInteressadoEstiverInativoNoCadastro() {
		// given
		Assunto assunto = new Assunto(1,"descricao",new Date(),Ativo.SIM.value());
		Interessado interessado = new Interessado(1,"Nome","Indentificacao",new Date(),Ativo.NAO.value());
		ProcessoDTOInput processoDTOinput = new ProcessoDTOInput();
		processoDTOinput.setCdAssunto(1);
		processoDTOinput.setCdInteressado(1);
		when(assuntoService.buscarAssunto(1)).thenReturn(assunto);
		when(interessadoService.buscarPorId(1)).thenReturn(interessado);
		// when
		// then
		assertThatThrownBy(() -> sut.cadastrarProcesso(processoDTOinput))
			.isInstanceOf(InactiveObjectException.class)
			.hasMessageContaining("Interessado inativo, não é possivel cadastrar o Processo!");
	}
	
	@Test
	void deveEstourarExcecaoSeAssuntoEstiverInativoNoCadastro() {
		// given
		Assunto assunto = new Assunto(1,"descricao",new Date(),Ativo.NAO.value());
		ProcessoDTOInput processoDTOinput = new ProcessoDTOInput();
		processoDTOinput.setCdAssunto(1);
		when(assuntoService.buscarAssunto(1)).thenReturn(assunto);
		// when
		// then
		assertThatThrownBy(() -> sut.cadastrarProcesso(processoDTOinput))
			.isInstanceOf(InactiveObjectException.class)
			.hasMessageContaining("Assunto inativo, não é possivel cadastrar o Processo!");
	}

	@Test
	void testAtualizarPorId() {
		// given
		Assunto assunto = new Assunto(1,"descricao",new Date(),Ativo.SIM.value());
		Interessado interessado = new Interessado(1,"Nome","Indentificacao",new Date(),Ativo.SIM.value());
		Assunto assuntoNovo = new Assunto(2,"descricao Novo",new Date(),Ativo.SIM.value());
		Interessado interessadoNovo = new Interessado(2,"Nome Novo","Indentificacao Novo",new Date(),Ativo.SIM.value());
		ProcessoDTOInput processoDTOinput = new ProcessoDTOInput();
		processoDTOinput.setCdAssunto(1);
		processoDTOinput.setCdInteressado(1);
		processoDTOinput.setId(1);
		processoDTOinput.setNuProcesso(1);
		processoDTOinput.setDescricao("Processo Teste Atualizado");
		processoDTOinput.setNuAno("2010");
		processoDTOinput.setSgOrgaoSetor("SOFT");
		processoDTOinput.setChaveProcesso("NULL");
		Processo processoResultado = new Processo(1,1,"TEST","2020","TEST 1/2020","Processo Teste",assunto,interessado);
		Processo processoAtualizado = new Processo(1,1,"SOFT","2010","NULL","Processo Teste Atualizado",assunto,interessado);
		when(assuntoService.buscarAssunto(1)).thenReturn(assuntoNovo);
		when(interessadoService.buscarPorId(1)).thenReturn(interessadoNovo);
		when(processoRepository.findById(1)).thenReturn(Optional.of(processoResultado));
		when(modelMapper.map(processoDTOinput, Processo.class)).thenReturn(processoAtualizado);
		
		// when
		sut.atualizarPorId(1, processoDTOinput);
		
		// then
		ArgumentCaptor<Processo> processoArgumentCaptor = ArgumentCaptor.forClass(Processo.class);
        verify(processoRepository).save(processoArgumentCaptor.capture());
        
        Processo capturedProcesso = processoArgumentCaptor.getValue();
        assertThat(capturedProcesso.getClass()).isEqualTo(Processo.class);
        assertThat(capturedProcesso.getAssunto()).isEqualTo(assuntoNovo);
        assertThat(capturedProcesso.getInteressado()).isEqualTo(interessadoNovo);
        assertThat(capturedProcesso.getChaveProcesso()).isEqualTo("SOFT 1/2010");
        assertThat(capturedProcesso.getNuAno()).isEqualTo("2010");
        assertThat(capturedProcesso.getDescricao()).isEqualTo("Processo Teste Atualizado");
        assertThat(capturedProcesso.getSgOrgaoSetor()).isEqualTo("SOFT");
        assertThat(capturedProcesso.getNuProcesso()).isEqualTo(1);
	}

	@Test
	void deletarProcessoPeloSeuID() {
		// when
		sut.apagarProcessoPorId(anyInt());
		
		// then
		verify(processoRepository).deleteById(anyInt());
	}

}
