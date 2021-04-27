package br.com.audoraselection.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.audoraselection.model.Usuario;
import br.com.audoraselection.security.UserCredentials;
import br.com.audoraselection.service.UserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

	private final String BASE_URL = "/users";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private UserService userService;


	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void post_para_criar_um_usuario_no_bancoDeDados() throws Exception {
		Usuario user = new Usuario();
		user.setId(2L);
		user.setEnabled(true);
		user.setPassword("secret");
		user.setUsername("miguel@mail.com");
		user.setAdministrador(false);

		when(userService.criar(user)).thenReturn(user);

		String json = mapper.writeValueAsString(user);

		mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/create").content(json)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	@Test
	public void post_criarUsuarioNoBancoDeDados_BadRequest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/create").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}

	@Test
	public void post_autenticar_com_usuario_inexistente() throws Exception {
		UserCredentials user = new UserCredentials("ghost@mail.com", "notexist",false);

		String json = mapper.writeValueAsString(user);

		mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL+"/auth")
											  .content(json)
											  .contentType(MediaType.APPLICATION_JSON)
											  .accept(MediaType.APPLICATION_JSON))
											  .andExpect(status().isUnauthorized());
	}

	@Test
	public void post_tentativa_autenticar_usuario_existente() throws Exception {
		UserCredentials user = new UserCredentials("admin@mail.com", "secret",true);

		String json = mapper.writeValueAsString(user);

		//Aqui tem que extarir do header
		mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL+"/auth")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());

	}

}
