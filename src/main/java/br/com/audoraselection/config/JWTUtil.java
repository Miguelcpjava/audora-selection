package br.com.audoraselection.config;

import java.io.Serializable;


import org.springframework.stereotype.Component;


@Component
public class JWTUtil implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8345907539842388040L;

	public static final long JWT_TOKEN_VALIDITY = 86400000;
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SECRET = "audorando";
	
	
	
	
	
	
}
