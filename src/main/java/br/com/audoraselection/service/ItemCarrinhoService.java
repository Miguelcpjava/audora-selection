package br.com.audoraselection.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.audoraselection.model.CarrinhoCompras;
import br.com.audoraselection.model.ItemCarrinho;
import br.com.audoraselection.repository.ItemCarrinhoRepository;

@Service
public class ItemCarrinhoService {

	@Autowired
	private ItemCarrinhoRepository itemCarrinhoRepository;
	
	public ItemCarrinho criar(ItemCarrinho item) {
		return itemCarrinhoRepository.save(item);
	}
	
	public ItemCarrinho atualizar(ItemCarrinho item) {
		return itemCarrinhoRepository.saveAndFlush(item);
	}
	
	public Optional<ItemCarrinho> buscarPorId(Long itemId) {
		return itemCarrinhoRepository.findById(itemId);
	}
	
	public List<ItemCarrinho> buscarListaPorCarrinho(CarrinhoCompras carrinho){
		return itemCarrinhoRepository.findByCarrinho(carrinho);
	}
	
	public void remover(ItemCarrinho item) {
		itemCarrinhoRepository.delete(item);
	}
}
