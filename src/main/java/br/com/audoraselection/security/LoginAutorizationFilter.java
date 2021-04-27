package br.com.audoraselection.security;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import br.com.audoraselection.config.JWTUtil;
import br.com.audoraselection.service.UserService;
import io.jsonwebtoken.Jwts;

public class LoginAutorizationFilter extends BasicAuthenticationFilter {

	private UserService usersevice;
	
	public LoginAutorizationFilter(AuthenticationManager authenticationManager, UserService userservice) {
		super(authenticationManager);
		this.usersevice = userservice;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String header = request.getHeader(JWTUtil.HEADER_STRING);
		if(header == null || !header.startsWith(JWTUtil.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
		UsernamePasswordAuthenticationToken authenticateToken = getAuthenticationToken(request);
		SecurityContextHolder.getContext().setAuthentication(authenticateToken);
		chain.doFilter(request, response);
	}
	
	private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
		String token = request.getHeader(JWTUtil.HEADER_STRING);
        if (token == null) return null;
        String username = Jwts.parser().setSigningKey(JWTUtil.SECRET)
                .parseClaimsJws(token.replace(JWTUtil.TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();
        UserDetails userDetails = usersevice.loadUserByUsername(username);
        return username != null ?
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()) : null;
    }
	
}
