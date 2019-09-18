package br.com.agrostok.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration("passwordEncoderProducer")
public class PasswordEncoderProducer {

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder enc = new BCryptPasswordEncoder();
		return enc;
	}
	
}
