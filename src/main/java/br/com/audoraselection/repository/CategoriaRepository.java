package br.com.audoraselection.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.audoraselection.model.CategoriaProduto;

public interface CategoriaRepository extends JpaRepository<CategoriaProduto, Long>{

	List<CategoriaProduto> findByNome(String nome);
}
