package br.com.audoraselection.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.audoraselection.config.JWTUtil;
import br.com.audoraselection.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private UserService userSevice;
	
	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService) {
		this.authenticationManager = authenticationManager;
		this.userSevice = userService;
		setFilterProcessesUrl("/users/auth"); 
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			UserCredentials user = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
			setDetails(request, token);
			UserDetails userDetails =  this.userSevice.loadUserByUsername(token.getName());
			if(userDetails.isEnabled()) {
				Authentication authentication = token;
				return authentication;
			}else {
				return this.authenticationManager.authenticate(token);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		String username =authResult.getName();
		String token = Jwts
                .builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + JWTUtil.JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, JWTUtil.SECRET)
                .compact();
        String bearerToken = JWTUtil.TOKEN_PREFIX + token;
        response.getWriter().write(bearerToken);
        response.addHeader(JWTUtil.HEADER_STRING, bearerToken);
	}

}
