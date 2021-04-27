package br.com.audoraselection.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.audoraselection.model.Descontos;

public interface DescontosRepository extends JpaRepository<Descontos, Long>{

	List<Descontos> findByAtivo(boolean ativo);
}
