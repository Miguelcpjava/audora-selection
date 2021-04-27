package br.com.audoraselection.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.audoraselection.model.CategoriaProduto;
import br.com.audoraselection.model.Produto;
import br.com.audoraselection.service.ProdutoService;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class ProdutoControllerTest {

	private final String BASE_URL ="/product";

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private ProdutoService produtoService;
	
	@MockBean
	private CategoriaProduto categoria;
	
	private String token;
	
	
	@BeforeEach
	void setUp() {
		  MockitoAnnotations.openMocks(this);
		  categoria = new CategoriaProduto("BISCOITO");
		  categoria.setId(1L);
		  token ="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkBtYWlsLmNvbSIsImV4cCI6MTYxOTYzMTQzM30.C0fgwakQW9HxiFvEDLGdGHsvK17aTL6p4GQYn6NDDU6nmYAapIeO4zO57g7Cd9d4vEg4qECAhHC-h9eL7VKyLw";
	}
	
	
	@Test
	public void consultar_um_produto_qualquer_pelo_id() throws Exception {
		Long id = 1L;
		
		when(produtoService.buscarPorId(any(Long.class))).thenReturn(any(Produto.class));
		
		mockMvc.perform(get(BASE_URL+"/consulta/{idProduct}",id)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	public void criar_um_novo_produto_sem_autencicacao() throws Exception {
		Produto novoProduto = new Produto("Cookie", new BigDecimal(3), categoria);
		
		when(produtoService.criar(any(Produto.class))).thenReturn(novoProduto);
		
		String json = objectMapper.writeValueAsString(novoProduto);
		
		System.out.println(json);
		
		mockMvc.perform(post(BASE_URL+"/create")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(status().isForbidden());
	}
	
	@Test
	//@WithUserDetails(value = "root")
	public void criar_um_novo_produto_autenticado() throws Exception {
		Produto novoProduto = new Produto("Cookie", new BigDecimal(3), categoria);
		
		when(produtoService.criar(any(Produto.class))).thenReturn(novoProduto);
		
		String json = objectMapper.writeValueAsString(novoProduto);
		
		System.out.println(json);
		
		mockMvc.perform(post(BASE_URL+"/create")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + token)
				.content(json))
		.andExpect(status().isCreated());
	}
}
