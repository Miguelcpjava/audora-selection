package br.com.audoraselection.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.audoraselection.exception.ResourceNotFoundException;
import br.com.audoraselection.model.CarrinhoCompras;
import br.com.audoraselection.model.ItemCarrinho;
import br.com.audoraselection.model.Produto;
import br.com.audoraselection.model.Usuario;
import br.com.audoraselection.service.CarrinhoComprasService;
import br.com.audoraselection.service.ItemCarrinhoService;
import br.com.audoraselection.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@RestController
@RequestMapping("/carrinho")
@Api(value="Carrinho", tags = "Carrinho de Compras")
public class CarrinhoComprasController {
	
	@Autowired
	private CarrinhoComprasService carrinhoComprasService;
	
	@Autowired
	private ItemCarrinhoService itemCarrinhoService;
	
	@Autowired
	private UserService userService;
	
	@Transactional(rollbackFor = Exception.class)
	public CarrinhoCompras criarCarrinhoDeComprasVazio(Usuario usuario) {
		CarrinhoCompras carrinho = new CarrinhoCompras();
		carrinho.setUsuario(usuario);
		carrinho.setSubtotal(BigDecimal.ZERO);
		carrinho = carrinhoComprasService.criar(carrinho);
		return carrinho;
	}
	
	public void salvarItensDeCarrinho(List<ItemCarrinho>itens) {
		if(itens.size() > 0 ) {
			for(ItemCarrinho item : itens) {
				itemCarrinhoService.criar(item);
			}
		}
	}
	
	@GetMapping("/status/{usuarioId}/{carrinhoId}")
	@ApiOperation(value = "Método para saber o status atual do carrinho",response = CarrinhoCompras.class)
	public ResponseEntity<?> statusCarrinho(@PathVariable Long usuarioId, @PathVariable Long carrinhoId) throws ResourceNotFoundException {
		Usuario usuario = new Usuario();
		usuario = userService.buscarPorId(usuarioId);
		CarrinhoCompras novoCarrinho = carrinhoComprasService.buscarCarrinhoAtivoPorUsuario(usuario,carrinhoId);
		return ResponseEntity.ok(novoCarrinho);
	}
	
	@GetMapping("/listarItem/{carrinhoId}")
	@ApiOperation(value = "Listar todos os itens de um determinado carrinho", response = ItemCarrinho[].class)
	public ResponseEntity<?> listarItensDeCarrinho(@PathVariable Long carrinhoId) throws ResourceNotFoundException{
		CarrinhoCompras carrinho = carrinhoComprasService.buscarCarrinhoPorID(carrinhoId).get();
		List<ItemCarrinho> produtos = itemCarrinhoService.buscarListaPorCarrinho(carrinho);
		if(produtos.size() > 0) {
			return ResponseEntity.ok(produtos);
		}else {
			return ResponseEntity.badRequest().body("Não há itens neste carrinho");
		}
	}
	
	
	
	@PostMapping("/adicionar/{usuarioId}/{carrinhoId}")
	@Transactional(rollbackFor = Exception.class)
	@ApiOperation(value = "Adicionar um novo produto no carrinho específico do usuário",response = Produto.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name= "Authorization", value = "Bearer token",
				required = true, dataType = "string", paramType = "header")
	})
	public ResponseEntity<?> adicionarProduto( @RequestBody Produto produto,
											@PathVariable Long carrinhoId,
											@PathVariable Long usuarioId) throws ResourceNotFoundException {
		CarrinhoCompras carrinho = carrinhoComprasService.buscarCarrinhoPorID(carrinhoId).orElse(new CarrinhoCompras());
		Usuario usuario = userService.buscarPorId(usuarioId);
		
		if(carrinho.getId() == null) {
			carrinho = criarCarrinhoDeComprasVazio(usuario);
		}
		
		ItemCarrinho item =  new ItemCarrinho(produto,1,produto.getPreco(),carrinho);
		List<ItemCarrinho> itens = adicionarItemNaListaDeProdutosDoCarrinho(carrinho, item);
		
		if (itens != null) {
			carrinho.setLinesItems(itens);
			atualizarValorSubtotalCarrinho(carrinho, itens);
		}
		
		CarrinhoCompras novoCarrinho = carrinhoComprasService.atualizar(carrinho);
		salvarItensDeCarrinho(itens);
		if(novoCarrinho != null) {
			return ResponseEntity.ok().build();
		}else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping("/delete/{carrinhoId}/{produtoId}")
	@ApiOperation(value = "Remover um produto de um carrinho de compras")
	public ResponseEntity<?> removerProduto(@PathVariable Long carrinhoId, @PathVariable Long produtoId) throws ResourceNotFoundException {
		List<ItemCarrinho> itens = new ArrayList<ItemCarrinho>();
		CarrinhoCompras carrinho = carrinhoComprasService.buscarCarrinhoPorID(carrinhoId).get();
		itens = itemCarrinhoService.buscarListaPorCarrinho(carrinho);
		boolean removed = false;
		String nomeProduto = "";
		for(ItemCarrinho item : itens) {
			if(item.getProduto().getId() == produtoId) {
				nomeProduto = item.getProduto().getNome();
				itemCarrinhoService.remover(item);
				removed = true;
			}
		}
		if(removed) {
			itens = itemCarrinhoService.buscarListaPorCarrinho(carrinho);
			atualizarValorSubtotalCarrinho(carrinho, itens);
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PatchMapping("/finalizar/{carrinhoId}")
	@ApiOperation(value = "Finalizar a compra")
	@ApiParam(value = "carrinhoId", example = "1")
	public ResponseEntity<?> finalizarCarrinho(@PathVariable Long carrinhoId) throws ResourceNotFoundException {
		CarrinhoCompras novoCarrinho = carrinhoComprasService.buscarCarrinhoPorID(carrinhoId).get();
		if (novoCarrinho.isFinalizado() == true) {
			return ResponseEntity.badRequest().body("Carrinho já finalizado!");
		} else {
			novoCarrinho.setFinalizado(true);
			try {
				carrinhoComprasService.atualizar(novoCarrinho);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ResponseEntity.ok(novoCarrinho);
		}
	}
	
	@PatchMapping("/alterar/quantidade/produto/aumentar/{carrinhoId}/{produtoId}")
	@ApiOperation(value = "Aumentar a quantidade do produto que está no carrinho de compras")
	public ResponseEntity<?> aumentarQuantidadeDoProduto(@PathVariable Long carrinhoId,@PathVariable Long produtoId) throws ResourceNotFoundException{
		CarrinhoCompras carrinho = carrinhoComprasService.buscarCarrinhoPorID(carrinhoId).get();
		List<ItemCarrinho> itens = new ArrayList<ItemCarrinho>();
		itens = itemCarrinhoService.buscarListaPorCarrinho(carrinho);
		if(itens.size() > 0) {
			for(ItemCarrinho item: itens) {
				if(item.getProduto().getId() == produtoId) {
					item.setQuantidade(item.getQuantidade()+1);
					itemCarrinhoService.atualizar(item);
				}
			}
			atualizarValorSubtotalCarrinho(carrinho, itens);
		}else {
			ResponseEntity.badRequest().body("Não há itens neste carrinho!");
		}
		return ResponseEntity.ok().build();
	}
	
	@PatchMapping("/alterar/quantidade/produto/diminuir/{carrinhoId}/{idProduto}")
	@ApiOperation(value = "Diminuir a quantidade do produto que está no carrinho de compras")
	public ResponseEntity<Void> diminuirQuantidadeDoProduto(@PathVariable Long carrinhoId,@PathVariable Long idProduto) throws ResourceNotFoundException{
		CarrinhoCompras carrinho = carrinhoComprasService.buscarCarrinhoPorID(carrinhoId).get();
		List<ItemCarrinho> itens = new ArrayList<ItemCarrinho>();
		itens = itemCarrinhoService.buscarListaPorCarrinho(carrinho);
		for(ItemCarrinho item: itens) {
			if(item.getProduto().getId() == idProduto) {
				if(item.getQuantidade() == 0) {
					ResponseEntity.badRequest().build();
				}else {
					item.setQuantidade(item.getQuantidade()-1);
					itemCarrinhoService.atualizar(item);
				}
			}
		}
		atualizarValorSubtotalCarrinho(carrinho, itens);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/limparCarrinho")
	@ApiOperation(value = "Resetar o carrinho de compras")
	public ResponseEntity<String> limparCarrinho(@RequestBody CarrinhoCompras carrinho){
		List<ItemCarrinho> itens = new ArrayList<ItemCarrinho>();
		itens = itemCarrinhoService.buscarListaPorCarrinho(carrinho);
		for(ItemCarrinho item : itens) {
			itemCarrinhoService.remover(item);
		}
		carrinho.setLinesItems(itens);
		carrinhoComprasService.atualizar(carrinho);
		if(itens.size() > 0 ) {
			return ResponseEntity.badRequest().body("Não foi possível limpar o carrinho!");
		}else {
			return ResponseEntity.ok().body("Carrinho limpo com sucesso!");
		}
	}
	
	public List<ItemCarrinho> adicionarItemNaListaDeProdutosDoCarrinho(CarrinhoCompras carrinho, ItemCarrinho item){
		List<ItemCarrinho> itens = new ArrayList<ItemCarrinho>();
		//Buscando itens já cadastrados no banco de dados
		itens = itemCarrinhoService.buscarListaPorCarrinho(carrinho);
		//Adicionando o novo item
		itens.add(item);
		return itens;
	}
	
	public void atualizarValorSubtotalCarrinho(CarrinhoCompras carrinho, List<ItemCarrinho> itens) {
		carrinho.setSubtotal(BigDecimal.ZERO);
		BigDecimal valor = BigDecimal.ZERO;
		if(itens.size() > 0) {
			for(ItemCarrinho item : itens) {
				valor = valor.add(item.getPreco()).multiply(new BigDecimal(item.getQuantidade()));
			}
			carrinho.setSubtotal(valor);
			carrinhoComprasService.atualizar(carrinho);
		}
	}
	
	
}
