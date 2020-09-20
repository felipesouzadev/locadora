package com.felipesouza.locadora.security;



import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.felipesouza.locadora.domain.model.Usuario;
import com.felipesouza.locadora.domain.repository.UsuarioRepository;

@Repository
@Transactional
public class ImplementsUserDetailsService implements UserDetailsService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(email);
		
		if(usuario == null) {
			throw new UsernameNotFoundException("Usuario nao encontrado!");
		}
		User user = new User(usuario.getUsername(), usuario.getPassword(), usuario.isEnabled(), 
				usuario.isAccountNonExpired(), usuario.isCredentialsNonExpired(), usuario.isAccountNonLocked(), usuario.getAuthorities()); 
		return user;
	}
	
	
	

}
