package br.com.audoraselection.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.audoraselection.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}
