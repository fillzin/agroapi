package br.com.agrostok.config.oauth;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.http.HttpMethod;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

//import br.com.agrostok.service.CustomUserDetailsService;

@Configuration
@EnableAuthorizationServer
@EnableMBeanExport(registration=RegistrationPolicy.IGNORE_EXISTING)
public class AuthorizationServerConfigurations extends AuthorizationServerConfigurerAdapter {

	private TokenStore tokenStore = new InMemoryTokenStore();
	private String cliente = "cliente";
	private String secret = "123";
	
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;
	
//	@Autowired
//	private CustomUserDetailsService customUserDetailsService;
	
	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
	}

	@Bean
	public JdbcClientDetailsService clientDetailsService() {
		return new JdbcClientDetailsService(dataSource);
	}

	
//	@Override
//	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//		endpoints.tokenStore(tokenStore());
//		endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
//		endpoints.authenticationManager(authenticationManager);
////			.userDetailsService(customUserDetailsService);
//	}
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.checkTokenAccess("permitAll()");
		oauthServer.allowFormAuthenticationForClients();
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore());
		endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
		endpoints.authenticationManager(authenticationManager);

	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(clientDetailsService());
//		clients.inMemory()
//			.withClient(this.cliente)
//			.secret(new BCryptPasswordEncoder().encode(this.secret))
//			.authorizedGrantTypes("password", "authorization_code", "refresh_token")
//			.scopes("bar","read","write")
//			.resourceIds(AuthorizationServerConfigurations.RESOURCE_ID)
//			.accessTokenValiditySeconds(60)
//			.refreshTokenValiditySeconds(60*60*24);
	}

//	@Bean
//	@Primary
//	public DefaultTokenServices tokenServices() {
//		DefaultTokenServices tokenServices = new DefaultTokenServices();
//		tokenServices.setSupportRefreshToken(true);
//		tokenServices.setAccessTokenValiditySeconds(0);
//		tokenServices.setTokenStore(tokenStore());
//		return tokenServices;
//	}
}
