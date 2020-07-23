package com.algaworks.algafood.core.security.authorizationServer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

public class JwtCustomCliamsTokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

		 if (authentication.getPrincipal() instanceof AuthUser) {
			
			var authUser = (AuthUser) authentication.getPrincipal();
	
			Map<String, Object> info = new HashMap<>(); 
			info.put("Nome_Completo", authUser.getFullName());
			info.put("Id_Usuario", authUser.getUserId());
			
			var auth2AccessToken = (DefaultOAuth2AccessToken) accessToken;
			auth2AccessToken.setAdditionalInformation(info);
		}
		 return accessToken;
			

}
	
}
