package br.com.audoraselection.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.audoraselection.model.Produto;
import br.com.audoraselection.service.ProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/product")
@Api(value="Produto", tags = "Produto")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;
	
	@PostMapping("/create")
	@Transactional(rollbackFor = Exception.class)
	@ApiOperation(value = "Método para criação de um novo produto")
	public ResponseEntity<Produto> criar(@RequestBody Produto produto){
		Produto novoProduto = new Produto();
		novoProduto = produtoService.criar(produto);
		if(novoProduto != null) {
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping(value = "/consulta/{idProduct}")
	@ApiOperation(value = "Consultado de um produto por ID")
	public @ResponseBody ResponseEntity<Produto> buscarProdutoPorID(@PathVariable("idProduct") Long idProduct) throws Exception  {
		Produto product = produtoService.buscarPorId(idProduct);
		return ResponseEntity.status(HttpStatus.OK).body(product);
	}
	
	@DeleteMapping("/delete/{idProduct}")
	@ApiOperation(value = "Método para remover um produto pela ID")
	public ResponseEntity<?> remover(@PathVariable Long idProduct) {
		try {
			produtoService.remover(idProduct);
		}catch(Exception exception) {
			exception.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.noContent().build();
	}
}
