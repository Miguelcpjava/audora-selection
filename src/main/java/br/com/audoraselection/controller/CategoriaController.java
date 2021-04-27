package br.com.audoraselection.controller;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.audoraselection.model.CategoriaProduto;
import br.com.audoraselection.service.CategoriaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/category")
@CrossOrigin
@Api(value="Categoria", tags = "Categoria de Produtos")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;
	
	
	@GetMapping("/list/{nome}")
	@ApiOperation(value = "Busca das categorias por um nome específico")
	public ResponseEntity<CategoriaProduto> listarPorNome(@PathVariable String nome){
		List<CategoriaProduto> categorias = new ArrayList<CategoriaProduto>();
		categorias = categoriaService.findByNome(nome);
		if(categorias.size() > 0) {
			return ResponseEntity.ok().body(categorias.get(0));
		}else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/create")
	@Transactional(rollbackFor = Exception.class)
	@ApiOperation(value = "Metodo para criar uma nova categoria de produto")
	public ResponseEntity<Serializable> criarCategoriaDeProduto(@RequestBody CategoriaProduto categoria) {
		CategoriaProduto novaCategoria = new CategoriaProduto();
		try {
			novaCategoria = categoriaService.criar(categoria);
			if (novaCategoria != null) {
				return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not possible to create a category!");
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return null;
	}
}
