package br.com.rgm.processos.controllers;

import br.com.rgm.processos.dto.AssuntoDTO;
import br.com.rgm.processos.entities.Assunto;
import br.com.rgm.processos.services.AssuntoService;
import br.com.rgm.processos.services.exceptions.ObjectNotFoundException;
import br.com.rgm.processos.utils.Ativo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AssuntoController.class)
class AssuntoControllerTest {

    static final String ASSUNTO_URI = "/v1/assuntos";
    static final Integer ID = 1;
    static final String DESCRICAO = "descricao";
    static final Date DATA = new Date();


    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AssuntoService assuntoService;

    @Test
    @DisplayName("Deve retornar uma lista de assuntos e status 200 - OK")
    public void buscarListaDeAssuntos() throws Exception {

        //given
        Assunto assunto1 = new Assunto();
        assunto1.setId(ID);
        assunto1.setDescricao(DESCRICAO);
        assunto1.setDtCadastro(DATA);
        assunto1.setFlAtivo(Ativo.SIM.value());
        Assunto assunto2 = new Assunto(2, "descricao2", DATA, Ativo.SIM.value());

        given(assuntoService.buscarTodosAssuntos(null)).willReturn(Arrays.asList(assunto1, assunto2));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(ASSUNTO_URI)
                .header("api-version", "v1")
                .accept(MediaType.APPLICATION_JSON);
        //when
        ResultActions perform = mvc.perform(request);

        //then
        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").isNotEmpty())
                .andExpect(jsonPath("$.[0].descricao").value("descricao"))
                .andExpect(jsonPath("$.[1].id").isNotEmpty())
                .andExpect(jsonPath("$.[1].descricao").value("descricao2"));
    }

    @Test
    @DisplayName("Deve retornar um assunto e status 200 - OK")
    void buscarAssuntoExistenteTest() throws Exception {

        //given
        Assunto assunto = new Assunto(1, "assunto1", DATA, Ativo.SIM.value());
        given(assuntoService.buscarAssunto(1)).willReturn(assunto);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(ASSUNTO_URI.concat("/1"))
                .header("api-version", "v1")
                .accept(MediaType.APPLICATION_JSON);
        //when
        ResultActions perform = mvc.perform(request);
        //then
        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("descricao").value("assunto1"))
                .andExpect(jsonPath("dtCadastro").isNotEmpty());

    }

    @Test
    @DisplayName("Deve retornar um JSON de erro e status 404 - Not found")
    void buscarAssuntoInexistenteTest() throws Exception {

        //given
        ObjectNotFoundException objectNotFoundException = new ObjectNotFoundException("error message");
        given(assuntoService.buscarAssunto(anyInt())).willThrow(objectNotFoundException);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(ASSUNTO_URI.concat("/999"))
                .header("api-version", "v1")
                .accept(MediaType.APPLICATION_JSON);
        //when
        ResultActions perform = mvc.perform(request);
        //then
        perform
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("statusCode").value("404"))
                .andExpect(jsonPath("timestamp").isNotEmpty())
                .andExpect(jsonPath("fields").doesNotExist())
                .andExpect(jsonPath("message").value("error message"));
    }

    @Test
    @DisplayName("Deve retornar o assunto criado e status 201 - CREATED")
    void cadastrarAssuntoTest() throws Exception {

        // given
        Assunto assunto = new Assunto();
        assunto.setDescricao(DESCRICAO);
        assunto.setDtCadastro(DATA);
        assunto.setId(ID);
        AssuntoDTO dto = new AssuntoDTO();
        dto.setDtCadastro(DATA);
        dto.setDescricao(DESCRICAO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(ASSUNTO_URI)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("api-version", "v1");

        when(assuntoService.cadastrarAssunto(any(Assunto.class))).thenReturn(assunto);

        // when
        ResultActions perform = mvc.perform(request);

        // then
        perform
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("dtCadastro").isNotEmpty())
                .andExpect(jsonPath("descricao").value(dto.getDescricao()));

    }

    @Test
    @DisplayName("Deve retornar Status 204 - No Content")
    void alterarAtivoAssuntoTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .put(ASSUNTO_URI.concat("/alterar-ativo/1"))
                .accept(MediaType.APPLICATION_JSON)
                .header("api-version", "v1"))
                .andExpect(status().isNoContent())
                .andReturn();

    }
}