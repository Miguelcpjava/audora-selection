package br.com.audoraselection.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.audoraselection.exception.ResourceNotFoundException;
import br.com.audoraselection.model.Produto;
import br.com.audoraselection.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	
	public Produto criar(Produto produto) {
		return produtoRepository.save(produto);
	}
	
	public Produto buscarPorId(Long produtoID) throws ResourceNotFoundException {
		return produtoRepository.findById(produtoID).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
	}

	public void remover(Long id) {
		produtoRepository.deleteById(id);
		
	}
}
