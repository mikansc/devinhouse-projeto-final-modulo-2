package br.com.rgm.processos.controllers;

import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.rgm.processos.dto.ProcessoDTOInput;
import br.com.rgm.processos.dto.ProcessoDTOOutput;
import br.com.rgm.processos.entities.Assunto;
import br.com.rgm.processos.entities.Interessado;
import br.com.rgm.processos.entities.Processo;
import br.com.rgm.processos.services.ProcessoService;
import br.com.rgm.processos.services.exceptions.InactiveObjectException;
import br.com.rgm.processos.services.exceptions.ObjectNotFoundException;
import br.com.rgm.processos.utils.Ativo;

@WebMvcTest(value = ProcessoController.class)
class ProcessoControllerTest {
	
	static final String PROCESSO_URI = "/v1/processos";
	private static int ID = 1;
	private static int NUPROCESSO = 1;
	private static String SGORGAOSETOR = "SOFT";
	private static String NUANO = "2020";
	private static String CHAVEPROCESSO = "SOFT 1/2020";
	private static String DESCRICAO = "Processo Teste";
	private static Assunto ASSUNTO = new Assunto(1,"descricao",new Date(),Ativo.SIM.value());
	private static Interessado INTERESSADO = new Interessado(1,"Nome","Indentificacao",new Date(),Ativo.SIM.value());
	private static Assunto ASSUNTODOIS = new Assunto(2,"descricao 2",new Date(),Ativo.SIM.value());
	private static Interessado INTERESSADODOIS = new Interessado(2,"Nome 2","Indentificacao 2",new Date(),Ativo.SIM.value());
	
	@Autowired
    MockMvc mvc;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProcessoService processoService;

    @Test
    @DisplayName("Deve retornar lista de processos completa e status 200 - OK")
	void testBuscarProcessosSemParametros() throws Exception {
		// given
    	Processo processo = new Processo(ID,NUPROCESSO,SGORGAOSETOR,NUANO,CHAVEPROCESSO,DESCRICAO,ASSUNTO,INTERESSADO);
    	Processo processodois = new Processo(2,2,SGORGAOSETOR,NUANO,"SOFT 2/2020",DESCRICAO,ASSUNTODOIS,INTERESSADODOIS);
    	given(processoService.buscarProcessos()).willReturn(Arrays.asList(processo, processodois));
    	
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders
    			.get(PROCESSO_URI)
    			.header("api-version", "v1")
                .accept(MediaType.APPLICATION_JSON);
    	// when
    	ResultActions perform = mvc.perform(request);
    	// then
    	perform
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[0].id").isNotEmpty())
        .andExpect(jsonPath("$.[1].id").isNotEmpty());
	}
    
    @Test
    @DisplayName("Deve retornar lista de processos que possuem um interessado especifico e status 200 - OK")
	void testBuscarProcessosPorInteressado() throws Exception {
    	// given
    	Processo processo = new Processo(ID,NUPROCESSO,SGORGAOSETOR,NUANO,CHAVEPROCESSO,DESCRICAO,ASSUNTO,INTERESSADODOIS);
    	Processo processodois = new Processo(2,2,SGORGAOSETOR,NUANO,"SOFT 2/2020",DESCRICAO,ASSUNTODOIS,INTERESSADODOIS);
    	given(processoService.buscarPorInteressado(2)).willReturn(Arrays.asList(processo, processodois));
    	
    	String query = String.format("?cd_interessado=%s", 2);
    	
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders
    			.get(PROCESSO_URI.concat(query))
    			.header("api-version", "v1")
                .accept(MediaType.APPLICATION_JSON);
    	// when
    	ResultActions perform = mvc.perform(request);
    	// then
    	perform
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[0].id").isNotEmpty())
        .andExpect(jsonPath("$.[0].assunto.id").value(processo.getAssunto().getId()))
        .andExpect(jsonPath("$.[1].id").isNotEmpty())
        .andExpect(jsonPath("$.[1].assunto.id").value(processodois.getAssunto().getId()));
	}
    
    @Test
    @DisplayName("Deve retornar lista de processos que possuem um assunto especifico e status 200 - OK")
	void testBuscarProcessosPorAssunto() throws Exception {
    	// given
    	Processo processo = new Processo(ID,NUPROCESSO,SGORGAOSETOR,NUANO,CHAVEPROCESSO,DESCRICAO,ASSUNTODOIS,INTERESSADO);
    	Processo processodois = new Processo(2,2,SGORGAOSETOR,NUANO,"SOFT 2/2020",DESCRICAO,ASSUNTODOIS,INTERESSADODOIS);
    	given(processoService.buscarPorAssunto(2)).willReturn(Arrays.asList(processo, processodois));
    	
    	String query = String.format("?cd_assunto=%s", 2);
    	
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders
    			.get(PROCESSO_URI.concat(query))
    			.header("api-version", "v1")
                .accept(MediaType.APPLICATION_JSON);
    	// when
    	ResultActions perform = mvc.perform(request);
    	// then
    	perform
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[0].id").isNotEmpty())
        .andExpect(jsonPath("$.[0].interessado.id").value(processo.getInteressado().getId()))
        .andExpect(jsonPath("$.[1].id").isNotEmpty())
        .andExpect(jsonPath("$.[1].interessado.id").value(processodois.getInteressado().getId()));
	}
    
    @Test
    @DisplayName("Teste para Falha na busca de processo por interessado, Deve retornar um JSON de erro e status 404 - Not found")
	void testBuscarProcessosComInteressadoInexistente() throws Exception {
    	// given
    	ObjectNotFoundException objectNotFoundException = new ObjectNotFoundException("error message");
    	given(processoService.buscarPorInteressado(2)).willThrow(objectNotFoundException);
    	
    	String query = String.format("?cd_interessado=%s", 2);
    	
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders
    			.get(PROCESSO_URI.concat(query))
    			.header("api-version", "v1")
                .accept(MediaType.APPLICATION_JSON);
    	// when
    	ResultActions perform = mvc.perform(request);
    	// then
    	perform
    		.andExpect(status().isNotFound())
    		.andDo(print())
    		.andExpect(jsonPath("statusCode").value("404"))
    		.andExpect(jsonPath("timestamp").isNotEmpty())
        	.andExpect(jsonPath("fields").doesNotExist())
        	.andExpect(jsonPath("message").value(objectNotFoundException.getMessage()));
    }
    
    @Test
    @DisplayName("Teste para Falha na busca de processo por assunto, Deve retornar um JSON de erro e status 404 - Not found")
	void testBuscarProcessosComAssuntoInexistente() throws Exception {
    	// given
    	ObjectNotFoundException objectNotFoundException = new ObjectNotFoundException("error message");
    	given(processoService.buscarPorAssunto(2)).willThrow(objectNotFoundException);
    	
    	String query = String.format("?cd_assunto=%s", 2);
    	
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders
    			.get(PROCESSO_URI.concat(query))
    			.header("api-version", "v1")
                .accept(MediaType.APPLICATION_JSON);
    	// when
    	ResultActions perform = mvc.perform(request);
    	// then
    	perform
    		.andExpect(status().isNotFound())
    		.andDo(print())
    		.andExpect(jsonPath("statusCode").value("404"))
    		.andExpect(jsonPath("timestamp").isNotEmpty())
        	.andExpect(jsonPath("fields").doesNotExist())
        	.andExpect(jsonPath("message").value(objectNotFoundException.getMessage()));
    }

	@Test
	@DisplayName("Deve retornar o processos com o ID informado e status 200 - OK")
	void testBuscarPorId() throws Exception {
		// given
    	Processo processo = new Processo(ID,NUPROCESSO,SGORGAOSETOR,NUANO,CHAVEPROCESSO,DESCRICAO,ASSUNTO,INTERESSADO);
    	given(processoService.buscarPorId(1)).willReturn(processo);
    	
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders
    			.get(PROCESSO_URI.concat("/1"))
    			.header("api-version", "v1")
                .accept(MediaType.APPLICATION_JSON);
    	// when
    	ResultActions perform = mvc.perform(request);
    	// then
    	perform
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("id").isNotEmpty());
	}
	
	@Test
    @DisplayName("Busca por ID: Deve retornar um JSON de erro e status 404 - Not found")
	void testBuscarPorIdInexistente() throws Exception {
		// given
    	ObjectNotFoundException objectNotFoundException = new ObjectNotFoundException("error message");
    	given(processoService.buscarPorId(1)).willThrow(objectNotFoundException);
    	
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders
    			.get(PROCESSO_URI.concat("/1"))
    			.header("api-version", "v1")
                .accept(MediaType.APPLICATION_JSON);
    	// when
    	ResultActions perform = mvc.perform(request);
    	// then
    	perform
    		.andExpect(status().isNotFound())
    		.andDo(print())
    		.andExpect(jsonPath("statusCode").value("404"))
    		.andExpect(jsonPath("timestamp").isNotEmpty())
        	.andExpect(jsonPath("fields").doesNotExist())
        	.andExpect(jsonPath("message").value(objectNotFoundException.getMessage()));
    }


	@Test
	@DisplayName("Deve retornar o processos com a Chave de Processo informada e status 200 - OK")
	void testBuscarPorChaveProcesso() throws Exception {
		// given
    	Processo processo = new Processo(ID,NUPROCESSO,SGORGAOSETOR,NUANO,CHAVEPROCESSO,DESCRICAO,ASSUNTO,INTERESSADO);
    	given(processoService.buscarPorChaveProcesso(CHAVEPROCESSO)).willReturn(processo);
    	
    	String query = String.format("/chave-processo?valor=%s", CHAVEPROCESSO);
    	
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders
    			.get(PROCESSO_URI.concat(query))
    			.header("api-version", "v1")
                .accept(MediaType.APPLICATION_JSON);
    	// when
    	ResultActions perform = mvc.perform(request);
    	// then
    	perform
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("id").isNotEmpty());
	}
	
	@Test
    @DisplayName("Busca por Chave Processo: Deve retornar um JSON de erro e status 404 - Not found")
	void testBuscarPorChaveProcessoInexistente() throws Exception {
		// given
    	ObjectNotFoundException objectNotFoundException = new ObjectNotFoundException("error message");
    	given(processoService.buscarPorChaveProcesso(CHAVEPROCESSO)).willThrow(objectNotFoundException);
    	
    	String query = String.format("/chave-processo?valor=%s", CHAVEPROCESSO);
    	
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders
    			.get(PROCESSO_URI.concat(query))
    			.header("api-version", "v1")
                .accept(MediaType.APPLICATION_JSON);
    	// when
    	ResultActions perform = mvc.perform(request);
    	// then
    	perform
    		.andExpect(status().isNotFound())
    		.andDo(print())
    		.andExpect(jsonPath("statusCode").value("404"))
    		.andExpect(jsonPath("timestamp").isNotEmpty())
        	.andExpect(jsonPath("fields").doesNotExist())
        	.andExpect(jsonPath("message").value(objectNotFoundException.getMessage()));
    }

	@Test
	@DisplayName("Deve retornar o processo cadastrado e o status 201 - CREATED")
	void testCadastrarProcesso() throws Exception {
		// given
		ProcessoDTOInput processoDTOInput = new ProcessoDTOInput();
		processoDTOInput.setId(ID);
		processoDTOInput.setNuProcesso(NUPROCESSO);
		processoDTOInput.setSgOrgaoSetor(SGORGAOSETOR);
		processoDTOInput.setNuAno(NUANO);
		processoDTOInput.setDescricao(DESCRICAO);
		processoDTOInput.setChaveProcesso(CHAVEPROCESSO);
		processoDTOInput.setCdAssunto(ASSUNTO.getId());
		processoDTOInput.setCdInteressado(INTERESSADO.getId());
		ProcessoDTOOutput processoDTOOutput = new ProcessoDTOOutput();
		processoDTOOutput.setId(ID);
		processoDTOOutput.setNuProcesso(NUPROCESSO);
		processoDTOOutput.setSgOrgaoSetor(SGORGAOSETOR);
		processoDTOOutput.setNuAno(NUANO);
		processoDTOOutput.setDescricao(DESCRICAO);
		processoDTOOutput.setChaveProcesso(CHAVEPROCESSO);
		processoDTOOutput.setAssunto(ASSUNTO);
		processoDTOOutput.setInteressado(INTERESSADO);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
    			.post(PROCESSO_URI)
    			.content(objectMapper.writeValueAsString(processoDTOInput))
                .contentType(MediaType.APPLICATION_JSON)
    			.header("api-version", "v1")
                .accept(MediaType.APPLICATION_JSON);
		
		when(processoService.cadastrarProcesso(any(ProcessoDTOInput.class))).thenReturn(processoDTOOutput);
    	// when
    	ResultActions perform = mvc.perform(request);
    	// then
    	perform
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("id").isNotEmpty())
        .andExpect(jsonPath("assunto.id").value(processoDTOOutput.getInteressado().getId()))
        .andExpect(jsonPath("interessado.id").value(processoDTOOutput.getInteressado().getId()));
	}
	
	@Test
	@DisplayName("Tenta cadastrar sem um projeto deve retornar um JSON de erro informando erro nos campos e o status 400 - Bad Request")
	void testCadastroSemUmProcesso() throws Exception {
		// given
		ProcessoDTOInput processoDTOInput = new ProcessoDTOInput();
		RuntimeException runtimeException = new RuntimeException("Error Message");
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
    			.post(PROCESSO_URI)
    			.content(objectMapper.writeValueAsString(processoDTOInput))
                .contentType(MediaType.APPLICATION_JSON)
    			.header("api-version", "v1")
                .accept(MediaType.APPLICATION_JSON);
		
		given(processoService.cadastrarProcesso(any(ProcessoDTOInput.class))).willThrow(runtimeException);
    	// when
    	ResultActions perform = mvc.perform(request);
    	// then
    	perform
    	.andExpect(status().isBadRequest())
        .andDo(print())
        .andExpect(jsonPath("statusCode").value("400"))
        .andExpect(jsonPath("timestamp").isNotEmpty())
        .andExpect(jsonPath("message").value("Um ou mais campos possuem um erro"))
        .andExpect(jsonPath("fields").isNotEmpty());
	}
	
	@Test
	@DisplayName("Tenta cadastrar um processo com objeto Inativo deve retornar um JSON de erro e o status")
	void testCadastroProcessoErroInativo() throws Exception {
		// given
		ProcessoDTOInput processoDTOInput = new ProcessoDTOInput();
		processoDTOInput.setId(ID);
		processoDTOInput.setNuProcesso(NUPROCESSO);
		processoDTOInput.setSgOrgaoSetor(SGORGAOSETOR);
		processoDTOInput.setNuAno(NUANO);
		processoDTOInput.setDescricao(DESCRICAO);
		processoDTOInput.setChaveProcesso(CHAVEPROCESSO);
		processoDTOInput.setCdAssunto(ASSUNTO.getId());
		processoDTOInput.setCdInteressado(INTERESSADO.getId());
		InactiveObjectException inactiveObjectException = new InactiveObjectException("Error Message Here");
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
    			.post(PROCESSO_URI)
    			.content(objectMapper.writeValueAsString(processoDTOInput))
                .contentType(MediaType.APPLICATION_JSON)
    			.header("api-version", "v1")
                .accept(MediaType.APPLICATION_JSON);
		
		given(processoService.cadastrarProcesso(any(ProcessoDTOInput.class))).willThrow(inactiveObjectException);
    	// when
    	ResultActions perform = mvc.perform(request);
    	// then
    	perform
    	.andExpect(status().isBadRequest())
        .andDo(print())
        .andExpect(jsonPath("statusCode").value("400"))
        .andExpect(jsonPath("timestamp").isNotEmpty())
        .andExpect(jsonPath("fields").doesNotExist())
        .andExpect(jsonPath("message").value(inactiveObjectException.getMessage()));
	}
	
	@Test
	@DisplayName("Deve atualizar um processo pelo seu ID e status 204 - No Content")
	void testAtualizarProcessoPorId() throws Exception {
		// given
		ProcessoDTOInput processoDTOInput = new ProcessoDTOInput();
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
    			.put(PROCESSO_URI.concat("/1"))
    			.content(objectMapper.writeValueAsString(processoDTOInput))
                .contentType(MediaType.APPLICATION_JSON)
    			.header("api-version", "v1")
                .accept(MediaType.APPLICATION_JSON);
		// when
        ResultActions perform = mvc.perform(request);

        // then
        perform
        	.andExpect(status().isNoContent());
	}

	@Test
	@DisplayName("Deve deletar um processo pelo seu ID e status 204 - No Content")
	void testApagarProcessoPorId() throws Exception {
		// given
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
    			.delete(PROCESSO_URI.concat("/1"))
    			.header("api-version", "v1")
                .accept(MediaType.APPLICATION_JSON);
		// when
		ResultActions perform = mvc.perform(request);

		// then
		perform
			.andExpect(status().isNoContent());
	}

}
