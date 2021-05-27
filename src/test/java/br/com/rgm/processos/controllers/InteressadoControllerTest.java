package br.com.rgm.processos.controllers;

import br.com.rgm.processos.dto.InteressadoDTO;
import br.com.rgm.processos.entities.Interessado;
import br.com.rgm.processos.services.InteressadoService;
import br.com.rgm.processos.services.exceptions.ObjectNotFoundException;
import br.com.rgm.processos.utils.Ativo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = InteressadoController.class)
class InteressadoControllerTest {

    static final String INTERESSADOS_URI = "/v1/interessados";
    private static int ID = 1;
    private static String NOME = "Nome";
    private static String CPF = "724.304.600-25";
    private static Date NASCIMENTO = new Date();

    @Autowired
    MockMvc mvc;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    InteressadoService interessadoService;


    @Test
    @DisplayName("Deve retornar lista de interessados e status 200 - OK")
    void buscarInteressados() throws Exception {
        // given
        Interessado interessado1 = new Interessado(ID, NOME, CPF, NASCIMENTO, Ativo.SIM.value());
        Interessado interessado2 = new Interessado();
        interessado2.setId(2);
        interessado2.setNmInteressado("Nome 2");
        interessado2.setNuIdentificacao("333.636.350-01");
        interessado2.setDtNascimento(new Date());
        interessado2.setFlAtivo(Ativo.SIM.value());

        given(interessadoService.buscarTodosInteressados(null)).willReturn(Arrays.asList(interessado1, interessado2));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(INTERESSADOS_URI)
                .header("api-version", "v1")
                .accept(MediaType.APPLICATION_JSON);

        // when
        ResultActions perform = mvc.perform(request);
        // then

        perform
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").isNotEmpty())
                .andExpect(jsonPath("$.[0].nmInteressado").value(interessado1.getNmInteressado()))
                .andExpect(jsonPath("$.[0].dtNascimento").isNotEmpty())
                .andExpect(jsonPath("$.[0].nuIdentificacao").value(interessado1.getNuIdentificacao()))
                .andExpect(jsonPath("$.[1].id").isNotEmpty())
                .andExpect(jsonPath("$.[1].nmInteressado").value(interessado2.getNmInteressado()))
                .andExpect(jsonPath("$.[1].dtNascimento").isNotEmpty())
                .andExpect(jsonPath("$.[1].nuIdentificacao").value(interessado2.getNuIdentificacao()));

    }

    @Test
    @DisplayName("Deve retornar um interessado pelo ID e status 200 - OK")
    void buscarUmInteressadoExistentePorId() throws Exception {
        //given
        Interessado interessado = new Interessado(ID, NOME, CPF, NASCIMENTO, Ativo.SIM.value());
        given(interessadoService.buscarPorId(anyInt())).willReturn(interessado);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(INTERESSADOS_URI.concat("/1"))
                .header("api-version", "v1")
                .accept(MediaType.APPLICATION_JSON);
        //when
        ResultActions perform = mvc.perform(request);
        //then
        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("nmInteressado").value(interessado.getNmInteressado()))
                .andExpect(jsonPath("dtNascimento").isNotEmpty())
                .andExpect(jsonPath("nuIdentificacao").value(interessado.getNuIdentificacao()));
    }

    @Test
    @DisplayName("Busca por ID: Deve retornar um JSON de erro e status 404 - Not found")
    void buscarUmInteressadoInexistentePorId() throws Exception {
        //given
        ObjectNotFoundException objectNotFoundException = new ObjectNotFoundException("error message");
        given(interessadoService.buscarPorId(anyInt())).willThrow(objectNotFoundException);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(INTERESSADOS_URI.concat("/999"))
                .header("api-version", "v1")
                .accept(MediaType.APPLICATION_JSON);
        //when
        ResultActions perform = mvc.perform(request);
        //then
        perform
                .andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(jsonPath("statusCode").value("404"))
                .andExpect(jsonPath("timestamp").isNotEmpty())
                .andExpect(jsonPath("fields").doesNotExist())
                .andExpect(jsonPath("message").value(objectNotFoundException.getMessage()));
    }

    @Test
    @DisplayName("Deve retornar um interessado pelo CPF e status 200 - OK")
    void buscarUmInteressadoExistentePorDocumento() throws Exception {
        //given
        Interessado interessado = new Interessado(ID, NOME, CPF, NASCIMENTO, Ativo.SIM.value());
        given(interessadoService.buscarPorDocumento(anyString())).willReturn(interessado);

        String query = String.format("?cpf=%s", interessado.getNuIdentificacao());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(INTERESSADOS_URI.concat("/documento").concat(query))
                .header("api-version", "v1")
                .accept(MediaType.APPLICATION_JSON);
        //when
        ResultActions perform = mvc.perform(request);
        //then
        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("nmInteressado").value(interessado.getNmInteressado()))
                .andExpect(jsonPath("dtNascimento").isNotEmpty())
                .andExpect(jsonPath("nuIdentificacao").value(interessado.getNuIdentificacao()));
    }

    @Test
    @DisplayName("Busca por documento: Deve retornar um JSON de erro e status 404 - Not found")
    void buscarUmInteressadoInexistentePorDocumento() throws Exception {
        //given
        ObjectNotFoundException objectNotFoundException = new ObjectNotFoundException("error message");
        given(interessadoService.buscarPorDocumento(anyString())).willThrow(objectNotFoundException);

        String query = "?cpf=999.999.999-99";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(INTERESSADOS_URI.concat("/documento").concat(query))
                .header("api-version", "v1")
                .accept(MediaType.APPLICATION_JSON);
        //when
        ResultActions perform = mvc.perform(request);
        //then
        perform
                .andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(jsonPath("statusCode").value("404"))
                .andExpect(jsonPath("timestamp").isNotEmpty())
                .andExpect(jsonPath("fields").doesNotExist())
                .andExpect(jsonPath("message").value(objectNotFoundException.getMessage()));
    }

    @Test
    @DisplayName("Deve retornar o interessado criado e status 201 - CREATED")
    void cadastrarInteressado() throws Exception {
        // given
        Interessado interessado = new Interessado(ID, NOME, CPF, NASCIMENTO, Ativo.SIM.value());
        InteressadoDTO dto = new InteressadoDTO();
        dto.setNmInteressado(NOME);
        dto.setNuIdentificacao(CPF);
        dto.setDtNascimento(NASCIMENTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(INTERESSADOS_URI)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("api-version", "v1");

        when(interessadoService.cadastrarInteressado(any(Interessado.class))).thenReturn(interessado);

        // when
        ResultActions perform = mvc.perform(request);

        // then
        perform
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("dtNascimento").isNotEmpty())
                .andExpect(jsonPath("nuIdentificacao").value(dto.getNuIdentificacao()))
                .andExpect(jsonPath("nmInteressado").value(dto.getNmInteressado()));
    }

    @Test
    @DisplayName("Ao tentar cadastrar um interessado repetido deve retornar um JSON de erro e o status 403 - Forbidden")
    void deveRetornarUmaExcecaoAoTentarCadastrarUmInteressadoQueJaEstaNoBanco() throws Exception {
        // given
    	DataIntegrityViolationException exception = new DataIntegrityViolationException("0");
    	InteressadoDTO dto = new InteressadoDTO();
        dto.setNmInteressado(NOME);
        dto.setNuIdentificacao(CPF);
        dto.setDtNascimento(NASCIMENTO);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(INTERESSADOS_URI)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("api-version", "v1");

        when(interessadoService.cadastrarInteressado(any(Interessado.class))).thenThrow(exception);

        // when
        ResultActions perform = mvc.perform(request);

        // then
        perform
                .andExpect(status().isForbidden())
                .andDo(print())
                .andExpect(jsonPath("statusCode").value("403"))
                .andExpect(jsonPath("timestamp").isNotEmpty())
                .andExpect(jsonPath("fields").doesNotExist())
                .andExpect(jsonPath("message").value("Operação não permitida. Contacte o administrador do sistema."));
    }
    
    @Test
    @DisplayName("Deve retornar Status 204 - No Content")
    void atualizarInteressado() throws Exception {
        // given
        InteressadoDTO dto = new InteressadoDTO();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(INTERESSADOS_URI.concat("/1"))
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("api-version", "v1");

        // when
        ResultActions perform = mvc.perform(request);

        // then
        perform
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar Status 204 - No Content")
    void alterarAtivoInteressado() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .put(INTERESSADOS_URI.concat("/alterar-ativo/1"))
                .accept(MediaType.APPLICATION_JSON)
                .header("api-version", "v1"))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}