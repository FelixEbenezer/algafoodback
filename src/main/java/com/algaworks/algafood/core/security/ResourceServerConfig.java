package com.algaworks.algafood.core.security;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			//	.antMatchers(HttpMethod.POST, "/v1/cozinhas").permitAll()
			.antMatchers("/v1/usuarios").permitAll()
			.anyRequest().authenticated()
			
			.and()
				.cors().and()
				.oauth2ResourceServer()
				//.opaqueToken();
				.jwt();
	}
	
/*	@Bean
	public JwtDecoder jwtDecoder() {
	 var secretKey = new SecretKeySpec("algaworksalgaworks123456789algaworksalgaworks123456789algaworksalgaworks123456789".getBytes(), "HmacSHA256");
	 return NimbusJwtDecoder.withSecretKey(secretKey).build();
	}
	*/
}