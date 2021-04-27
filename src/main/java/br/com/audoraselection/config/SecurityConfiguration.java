package br.com.audoraselection.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import br.com.audoraselection.security.JWTAuthenticationFilter;
import br.com.audoraselection.security.LoginAutorizationFilter;
import br.com.audoraselection.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;
	
	 private static final String[] AUTH_WHITELIST = {
	            // -- Swagger UI v2
	            "/v2/api-docs",
	            "/swagger-resources",
	            "/swagger-resources/**",
	            "/configuration/ui",
	            "/configuration/security",
	            "/swagger-ui.html",
	            "/webjars/**",
	            // -- Swagger UI v3 (OpenAPI)
	            "/v3/api-docs/**",
	            "/swagger-ui/**"
	            // other public endpoints of your API may be appended to this array
	    };


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
 		auth.userDetailsService(userService).passwordEncoder(encoder());
	}
  	@Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors()
			.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
			.and()
			.csrf().disable()
			.authorizeRequests()
			.antMatchers(AUTH_WHITELIST).permitAll()
			.antMatchers(HttpMethod.POST, "/users/**")
			.permitAll()
			.antMatchers(HttpMethod.GET, "/product/consulta/*").hasAnyAuthority("ROLE_C")
			.anyRequest().authenticated()
			.and()
            .addFilter(new JWTAuthenticationFilter(authenticationManager(),userService))
            .addFilter(new LoginAutorizationFilter(authenticationManager(), userService));
	}
	
}
