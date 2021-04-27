package br.com.audoraselection.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.audoraselection.model.Descontos;
import br.com.audoraselection.service.DescontosService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/discount")
@Api(value = "Descontos", tags = "Descontos")
public class DescontosController {

	@Autowired
	private DescontosService descontoService;
	
	@PostMapping("/create")
	@ApiOperation(value = "Método para criação de descontos")
	public ResponseEntity<Descontos> criar(@RequestBody Descontos descontos) {
		Descontos novoDesconto = new Descontos();
		try {
			novoDesconto = descontoService.criar(descontos);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		if (novoDesconto != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(novoDesconto);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
}
