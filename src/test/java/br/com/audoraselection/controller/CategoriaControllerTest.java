package br.com.audoraselection.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;

import static org.mockito.BDDMockito.*;

import br.com.audoraselection.model.CategoriaProduto;
import br.com.audoraselection.service.CategoriaService;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class CategoriaControllerTest {
	
	private final String BASE_URL = "/category";
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CategoriaService categoriaService;
	@Autowired
	private ObjectMapper objectMapper;
	
	private String token;
	
	@BeforeEach
	void setUp() {
		  MockitoAnnotations.openMocks(this);
		  token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkBtYWlsLmNvbSIsImV4cCI6MTYxOTYzMTQzM30.C0fgwakQW9HxiFvEDLGdGHsvK17aTL6p4GQYn6NDDU6nmYAapIeO4zO57g7Cd9d4vEg4qECAhHC-h9eL7VKyLw";
	}
	
	
	@Test
	public void buscar_uma_categoria_especifica() throws Exception {
		String categoria = "Biscoito";
		
		when(categoriaService.findByNome(any(String.class))).thenReturn(new ArrayList<>());
		
		mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL+"/list/{nome}",categoria)
				.header("Authorization", "Bearer " + TestUtil.token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void buscar_uma_categoria_especifica_nao_existente() throws Exception {
		String categoria = "Sabao";
		
		when(categoriaService.findByNome(any(String.class))).thenReturn(new ArrayList<>());
		
		mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL+"/list/{nome}",categoria)
				.header("Authorization", "Bearer " + TestUtil.token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void buscar_uma_categoria_especifica_sem_autenticacao() throws Exception {
		String categoria = "Biscoito";
		
		when(categoriaService.findByNome(any(String.class))).thenReturn(new ArrayList<>());
		
		mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL+"/list/{nome}",categoria)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}
	
	@Test
	public void criar_nova_categoria_de_produto() throws Exception {
		CategoriaProduto categoria = new CategoriaProduto("Higiene");
		categoria.setId(2L);
		
		when(categoriaService.criar(any(CategoriaProduto.class))).thenReturn(categoria);
		
		String json = objectMapper.writeValueAsString(categoria);
		
		mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL+"/create")
				.header("Authorization", "Bearer " + token)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}
}
