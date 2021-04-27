package br.com.audoraselection.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.audoraselection.model.Usuario;
import br.com.audoraselection.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/users")
@Api(value="User", tags = "Usuários")
public class UserController {
	

	@Autowired
	private UserService userService;
	
	@PostMapping("/create")
	@Transactional(rollbackFor = Exception.class)
	@ApiOperation(value = "Está operação tem como ação a criação do usuário no banco de dados")
	public ResponseEntity<Void> criar(@RequestBody Usuario user) {	
		Usuario userCreated = new Usuario();
		userCreated =  userService.criar(user);
		if (userCreated.getId() != null) {
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	

}
