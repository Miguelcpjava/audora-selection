package br.com.audoraselection.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.audoraselection.model.CarrinhoCompras;
import br.com.audoraselection.model.Usuario;

public interface CarrinhoComprasRepository extends JpaRepository<CarrinhoCompras, Long>{
	
	List<CarrinhoCompras> findByUsuario(Usuario usuario);
}
