package br.com.audoraselection.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.audoraselection.model.Descontos;
import br.com.audoraselection.security.UserCredentials;
import br.com.audoraselection.service.DescontosService;
import br.com.audoraselection.util.TipoPagamento;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class DescontoControllerTest {

	private final String BASE_URL = "/descontos";
	
	@Autowired
	 private TestRestTemplate restTemplate;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private DescontosService descontoService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	 private HttpEntity<Void> clientHeader;
	 private HttpEntity<Void> adminHeader;
	
	@BeforeEach
	void setUp() {
		  MockitoAnnotations.openMocks(this);
	}
	
	@BeforeEach
	public void configClientUser() throws JsonProcessingException {
		UserCredentials credentials = new UserCredentials("miguel@mail.com", "secret",false);
		String str = objectMapper.writeValueAsString(credentials);

		HttpHeaders headers = restTemplate.postForEntity(BASE_URL + "/auth", str, String.class).getHeaders();
		this.adminHeader = new HttpEntity<>(headers);
	}

	@BeforeEach
	public void configAdminUser() throws JsonProcessingException {
		UserCredentials credentials = new UserCredentials("admin@mail.com", "secret",true);
		String str = objectMapper.writeValueAsString(credentials);

		HttpHeaders headers = restTemplate.postForEntity(BASE_URL + "/auth", str, String.class).getHeaders();
		this.adminHeader = new HttpEntity<>(headers);
	}
	
	@Test
	public void criar_um_novo_desconto() throws Exception {
		Descontos desconto = new Descontos();
		desconto.setAtivo(true);
		desconto.setAplica_id(1L);
		desconto.setAplicabilidade("P");
		desconto.setCumulativo(true);
		desconto.setCupom("VALE05");
		desconto.setFormaPagamento(TipoPagamento.AVISTA.toString());
		desconto.setValorDesconto(5);
		desconto.setTipo("V");
		desconto.setTipoPagamentoComCartao("");
		
		String json = objectMapper.writeValueAsString(desconto);
		System.out.println(json);
		
		mockMvc.perform(post(BASE_URL + "/create").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isCreated()).andReturn();
	}
	
}
