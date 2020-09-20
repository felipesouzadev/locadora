package com.felipesouza.locadora.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.felipesouza.locadora.domain.exception.NegocioException;
import com.felipesouza.locadora.domain.model.Usuario;
import com.felipesouza.locadora.domain.repository.RoleRepository;
import com.felipesouza.locadora.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	public List<Usuario> listar(){
		return usuarioRepository.findAll();
	}
	
	public Usuario salvar(Usuario usuario) {
		Usuario usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
		
		if (usuarioExistente != null && !usuarioExistente.equals(usuario)) {
			throw new NegocioException("E-mail '" + usuario.getEmail() + "' ja cadastrado.");
		}
		usuario.adicionarRole((roleRepository.findByNome("ROLE_USER")));
		usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
		return usuarioRepository.save(usuario);
	}
	
	public Usuario salvarUsuario(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	public Usuario buscarPorEmail(String email) {
		return usuarioRepository.findByEmail(email);
	}
	
	public void excluir(Long usuarioId) {
		usuarioRepository.deleteById(usuarioId);
	}
}