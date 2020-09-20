package com.felipesouza.locadora.domain.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Filme {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String titulo;

	@NotBlank
	private String diretor;
	
	@NotNull
	private Integer	copias;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "filmesLocados")
	private List<Usuario> alocadores = new ArrayList<>();

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDiretor() {
		return diretor;
	}
	public void setDiretor(String diretor) {
		this.diretor = diretor;
	}
	public Integer getCopias() {
		return copias;
	}
	public void setCopias(Integer copias) {
		this.copias = copias;
	}
	
	public void alugar(Usuario usuario) {
		alocadores.add(usuario);
	}
	public List<Usuario> getAlocadores() {
		return alocadores;
	}
	
}
