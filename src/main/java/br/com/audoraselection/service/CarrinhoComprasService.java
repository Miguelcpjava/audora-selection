package br.com.audoraselection.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.audoraselection.exception.ResourceNotFoundException;
import br.com.audoraselection.model.CarrinhoCompras;
import br.com.audoraselection.model.Usuario;
import br.com.audoraselection.repository.CarrinhoComprasRepository;

@Service
public class CarrinhoComprasService {
	
	@Autowired
	private CarrinhoComprasRepository carrinhoComprasRepository;
	
	
	public CarrinhoCompras criar(CarrinhoCompras carrinho) {
		return carrinhoComprasRepository.save(carrinho);
	}
	
	public CarrinhoCompras atualizar(CarrinhoCompras carrinho) {
		return carrinhoComprasRepository.saveAndFlush(carrinho);
	}
	
	public void remover(CarrinhoCompras carrinho) {
		carrinhoComprasRepository.delete(carrinho);
	}
	
	public boolean carrinhoExiste(Long carrinhoId) {
		return carrinhoComprasRepository.existsById(carrinhoId);
	}
	
	public Optional<CarrinhoCompras> buscarCarrinhoPorID(Long id) throws ResourceNotFoundException {
		return Optional.ofNullable(carrinhoComprasRepository.findById(id)).orElseThrow(() -> new ResourceNotFoundException("Este carrinho não existe!"));
	}

	public List<CarrinhoCompras> buscarCarrinhoPorUsuario(Usuario usuario){
		return carrinhoComprasRepository.findByUsuario(usuario);
	}
	
	public CarrinhoCompras buscarCarrinhoAtivoPorUsuario(Usuario usuario, Long carrinhoID) throws ResourceNotFoundException{
		List<CarrinhoCompras> novoCarrinho = new ArrayList<CarrinhoCompras>();
		novoCarrinho = buscarCarrinhoPorUsuario(usuario);
		for(CarrinhoCompras carroDeCompras : novoCarrinho ) {
			if(carroDeCompras.getId() == carrinhoID && carroDeCompras.isFinalizado() == false) {
				return carroDeCompras;
			}
		}
		return new CarrinhoCompras();
	}
}
