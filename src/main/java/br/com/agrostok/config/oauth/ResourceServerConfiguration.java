package br.com.agrostok.config.oauth;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	private static final String RESOURCE_ID = "app";
	
	@Autowired
    private DataSource dataSource;
	
    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }
    
    @Bean
	public JdbcClientDetailsService clientDetailsService() {
		return new JdbcClientDetailsService(dataSource);
	}
    
    @Bean
    @Primary
    public AuthorizationServerTokenServices authorizationServerTokenServices() {
    	DefaultTokenServices tokenServices = new DefaultTokenServices();
    	tokenServices.setSupportRefreshToken(true);
    	tokenServices.setReuseRefreshToken(false);
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setClientDetailsService(clientDetailsService());
        return tokenServices;
    }
    
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_ID).tokenStore(tokenStore());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/usuarios/recuperarSenha", "/v2/api-docs", "/configuration/**", "/swagger-resources/**",
						"/swagger-ui.html", "/webjars/**", "/api-docs/**"
						,"/oauth/token","*/oauth/token","**/oauth/token","/usuarios","**/usuarios","*/usuarios")
				.permitAll().antMatchers(HttpMethod.GET, "/**").access("#oauth2.hasScope('READ')")
				.antMatchers(HttpMethod.OPTIONS, "/**").access("#oauth2.hasScope('READ')")
				.antMatchers(HttpMethod.POST, "/**").access("#oauth2.hasScope('WRITE')")
				.antMatchers(HttpMethod.PUT, "/**").access("#oauth2.hasScope('WRITE')")
				.antMatchers(HttpMethod.PATCH, "/**").access("#oauth2.hasScope('WRITE')")
				.antMatchers(HttpMethod.DELETE, "/**").access("#oauth2.hasScope('WRITE')");

		http.headers().frameOptions().disable();
		http.csrf().disable();

	}
}
