package br.com.audoraselection.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserCredentials {

	private String username;
	private String password;
	private boolean admin;
	
}
