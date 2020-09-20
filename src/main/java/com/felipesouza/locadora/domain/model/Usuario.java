package com.felipesouza.locadora.domain.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class Usuario implements UserDetails{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Email
	@NotBlank
	private String  email;
	
	@NotBlank
	private	String  nome;
	
	@NotBlank

	@JsonProperty(access = Access.WRITE_ONLY)
	private String 	senha;
	
	@ManyToMany
	@JoinTable(name = "usuario_role",
	joinColumns = @JoinColumn( name = "usuario_id"),
	inverseJoinColumns = @JoinColumn(name= "role_id"))
	@JsonIgnore
	private List<Role> roles = new ArrayList<>();
	
	@ManyToMany
	@JoinTable(name = "locacao",
	joinColumns = @JoinColumn(name = "usuario_id"),
	inverseJoinColumns = @JoinColumn(name ="filme_id"))
	@JsonIgnore
	private List<Filme> filmesLocados = new ArrayList<>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public void alugar(Filme filme) {
		filmesLocados.add(filme);
	}
	
	public void devolver(Filme filme) {
		filmesLocados.remove(filme);
	}
	public List<Filme> getFilmesLocados() {
		return filmesLocados;
	}
	
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public void adicionarRole(Role role) {
		this.roles.add(role);
	}
	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		Collection<GrantedAuthority> retorno = new ArrayList<>();
		roles.forEach(role -> retorno.add(new SimpleGrantedAuthority(role.getNome())));
		return retorno;
	}
	@Override
	@JsonIgnore
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.senha;
	}
	@Override
	@JsonIgnore
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}
	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	@JsonIgnore
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
}