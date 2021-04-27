package br.com.audoraselection.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.audoraselection.model.CarrinhoCompras;
import br.com.audoraselection.model.ItemCarrinho;

public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {
	
	List<ItemCarrinho> findByCarrinho(CarrinhoCompras carrinho);

}
