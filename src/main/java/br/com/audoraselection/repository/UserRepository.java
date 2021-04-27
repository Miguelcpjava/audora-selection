package br.com.audoraselection.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import br.com.audoraselection.model.Usuario;

public interface UserRepository extends JpaRepository<Usuario, Long>{

	 Usuario findByUsername(String username);

}
