//package br.com.agrostok.service;
//
//import java.util.Collection;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import br.com.agrostok.entity.User;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService{
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		
//		return new UserRepositoryUserDetails(new User());
//	}
//	
//	private final static class UserRepositoryUserDetails extends User implements UserDetails{
//
//		public UserRepositoryUserDetails(User user) {
//			super(user);
//		}
//		
//		@Override
//		public Collection<? extends GrantedAuthority> getAuthorities() {
//			return null;
//		}
//
//		@Override
//		public String getUsername() {
//			return getLogin();
//		}
//
//		@Override
//		public boolean isAccountNonExpired() {
//			// TODO Auto-generated method stub
//			return true;
//		}
//
//		@Override
//		public boolean isAccountNonLocked() {
//			// TODO Auto-generated method stub
//			return true;
//		}
//
//		@Override
//		public boolean isCredentialsNonExpired() {
//			// TODO Auto-generated method stub
//			return true;
//		}
//
//		@Override
//		public boolean isEnabled() {
//			// TODO Auto-generated method stub
//			return getAtivo();
//		}
//		
//	}
//
//}
