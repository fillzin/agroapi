package br.com.agrostok.config.oauth;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.agrostok.entity.User;
import br.com.agrostok.repository.UserRepository;

@Component("defaultAuthProvider")
//@Log4j2
public class CustomAuthenticationProvider implements AuthenticationProvider {


	Logger log = LogManager.getRootLogger();

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	

	@Transactional
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getPrincipal().toString();
		String password = authentication.getCredentials().toString();
		
		log.debug("Validacao de login para o usuario #" + username);
		
		User usuario = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Login e/ou senhas incorretos!"));
		
		if (!passwordEncoder.matches(password, usuario.getPassword())) {						
			throw new UsernameNotFoundException("Login e/ou senhas incorretos!");
		}
//		
//		Set<Role> roles = new HashSet<>();
//		for(Acesso acesso: usuario.getAcessos()) {
//			roles.addAll(acesso.getPerfil().getRoles());
//		}
		return new UsernamePasswordAuthenticationToken(usuario, null, null);

	}


	public boolean supports(Class<?> authentication) {
		return true;
	}

}