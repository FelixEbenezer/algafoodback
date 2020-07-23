package com.algaworks.algafood.core.security.authorizationServer;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

//	@Autowired
	//private RedisConnectionFactory redisConnectionFactory;
	
	@Autowired
	private JwtKeyStoreProperties jwtKeyStoreProperties;  
	
	@Autowired
	private PasswordEncoder passwordEncoder; 
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService; 
	
	@Autowired
	private DataSource dataSource; 
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
	
		
		clients.jdbc(dataSource);
		/*
		clients.inMemory()
				.withClient("algafood-web")
				.secret(passwordEncoder.encode("web123"))
				.authorizedGrantTypes("password", "refresh_token")
				.accessTokenValiditySeconds(6*24*60*60)
				.refreshTokenValiditySeconds(12*24*60*60)
				.scopes("WRITE", "READ")
		.and()
				.withClient("alga-backend")
				.secret(passwordEncoder.encode("123"))
				.authorizedGrantTypes("client_credentials")
				.scopes("WRITE")
				
		.and()
				  .withClient("alga-implicit")
				  .authorizedGrantTypes("implicit")
				  .scopes("write", "read")
				  .redirectUris("http://aplicacao-cliente")

		.and()
			  .withClient("alga-code")
			  .secret(passwordEncoder.encode("code123"))
			  .authorizedGrantTypes("authorization_code")
			  .scopes("READ")
			//  .redirectUris("http://aplicacao-cliente");
			 // .redirectUris("http://www.foodanalytics.local:8082");
	           .redirectUris("http://localhost:8082");				
	           
	           */

		
	}
	
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		var enhancerChain = new TokenEnhancerChain();
		enhancerChain.setTokenEnhancers(Arrays.asList(new JwtCustomCliamsTokenEnhancer(), jwtAccessTokenConverter()));
		
		endpoints
			.authenticationManager(authenticationManager)
			.userDetailsService(userDetailsService)
			.reuseRefreshTokens(false)
			.accessTokenConverter(jwtAccessTokenConverter())
			.tokenEnhancer(enhancerChain)
			.approvalStore(approvalStore(endpoints.getTokenStore()))
			.tokenGranter(tokenGranter(endpoints));
	}
	
	private ApprovalStore approvalStore(TokenStore tokenStore) {
		var approvalStore = new TokenApprovalStore();
		approvalStore.setTokenStore(tokenStore);
		
		return approvalStore;
	}
	/*
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	endpoints.authenticationManager(authenticationManager)
			 .userDetailsService(userDetailsService)
			 .reuseRefreshTokens(false)
		//	 .tokenStore(redisTokenStore())
			 .accessTokenConverter(jwtAccessTokenConverter())
			 .approvalStore(approvalStore(endpoints.getTokenStore()))
			 .tokenGranter(tokenGranter(endpoints));
	}
	
	private ApprovalStore approvalStore(TokenStore tokenStore) {
		 var approvalStore = new TokenApprovalStore();
		 approvalStore.setTokenStore(tokenStore);
		return approvalStore;
		}
	
	*/
	
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
	 JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
	 //jwtAccessTokenConverter.setSigningKey("algaworksalgaworks123456789algaworksalgaworks123456789algaworksalgaworks123456789");
	
	 // var jksResource = new ClassPathResource(jwtKeyStoreProperties.getPath());
	    var keyStorePass = jwtKeyStoreProperties.getPassword();
	    var keyPairAlias = jwtKeyStoreProperties.getKeypairAlias();
	    
	 //   var keyStoreKeyFactory = new KeyStoreKeyFactory(jksResource, keyStorePass.toCharArray());
	    var keyStoreKeyFactory = new KeyStoreKeyFactory(jwtKeyStoreProperties.getPath(), keyStorePass.toCharArray());
		 
	    var keyPair = keyStoreKeyFactory.getKeyPair(keyPairAlias);
	    
	    jwtAccessTokenConverter.setKeyPair(keyPair);
	 
	 return jwtAccessTokenConverter;
	}
	
	/*
	private TokenStore redisTokenStore() {
		return new RedisTokenStore(redisConnectionFactory);
	}
	*/
	private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
		var pkceAuthorizationCodeTokenGranter = new PkceAuthorizationCodeTokenGranter(endpoints.getTokenServices(),
				endpoints.getAuthorizationCodeServices(), endpoints.getClientDetailsService(),
				endpoints.getOAuth2RequestFactory());
		
		var granters = Arrays.asList(
				pkceAuthorizationCodeTokenGranter, endpoints.getTokenGranter());
		
		return new CompositeTokenGranter(granters);
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security
		.checkTokenAccess("isAuthenticated()")
		.tokenKeyAccess("permitAll()");
	}
	
	
}
