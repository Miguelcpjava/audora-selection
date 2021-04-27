package br.com.audoraselection.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.audoraselection.model.CategoriaProduto;
import br.com.audoraselection.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	
	public CategoriaProduto criar(CategoriaProduto categoria) {
		return categoriaRepository.save(categoria);
	}
	
	public List<CategoriaProduto> findByNome(String name){
		return categoriaRepository.findByNome(name);
	}
	
	public void atualizar(CategoriaProduto categoria) {
		categoriaRepository.saveAndFlush(categoria);
	}
	
	public void remover(CategoriaProduto categoria) {
		categoriaRepository.delete(categoria);
	}
	
}
