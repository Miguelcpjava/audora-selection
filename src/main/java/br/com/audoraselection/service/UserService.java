package br.com.audoraselection.service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.audoraselection.exception.ResourceNotFoundException;
import br.com.audoraselection.model.Usuario;
import br.com.audoraselection.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {


	private final UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public Usuario criar(Usuario user) {
		if (validarEmail(user.getUsername()) == true) {
			return userRepository.save(user);
		} else {
			return null;
		}
	}
	public Usuario atualizar(Usuario user) {
		Usuario userFound = userRepository.findById(user.getId()).get();
		if(userFound.getId() != null && validarEmail(user.getUsername())) {
			return userRepository.save(user);
		}else {
			return null;
		}
		
	}
	public Usuario buscarPorId(Long id) throws ResourceNotFoundException {
		return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não existe!"));
	}
	
	public Usuario buscarPorUsername(String username) {
		System.out.println(username);
		return userRepository.findByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 Usuario user = Optional.ofNullable(userRepository.findByUsername(username))
	                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
		 	List<GrantedAuthority> authorityListAdmin = AuthorityUtils.createAuthorityList("ROLE_A","ROLE_C");
	        List<GrantedAuthority> authorityListUser = AuthorityUtils.createAuthorityList("ROLE_C");
	        UserDetails userDetail = new User(user.getUsername(), 
					  user.getPassword() , 
					  user.isAdministrador() ? authorityListAdmin : authorityListUser );
			return userDetail;

		}
	
	
	public boolean validarEmail(String username) {
		Matcher matcher = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(username);
        return matcher.find();
	}
	
}
