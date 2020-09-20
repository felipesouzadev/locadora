package com.felipesouza.locadora.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felipesouza.locadora.domain.exception.NegocioException;
import com.felipesouza.locadora.domain.model.Filme;
import com.felipesouza.locadora.domain.repository.FilmeRepository;


@Service
public class CadastroFilmeService {
	
	@Autowired
	private FilmeRepository filmeRepository;
	
	public Filme salvar(Filme filme) {
		Filme filmeExistente = filmeRepository.findByTitulo(filme.getTitulo());
		
		if (filmeExistente != null && !filmeExistente.equals(filme)) {
			throw new NegocioException("Filme '" + filme.getTitulo() + "' já cadastrado.");
		}
		
		return filmeRepository.save(filme);
	}
	
	public List<Filme> listar(){
		return filmeRepository.findAll();
	}
	public List<Filme> listarDisponiveis(){
		return filmeRepository.findAllDisponivel();
	}
	
	public List<Filme> listarPorTitulo(String titulo){
		return filmeRepository.findByTituloContaining(titulo);
	}
	
	public Filme buscarPorTitulo(String titulo) {
		return filmeRepository.findByTitulo(titulo);
	}
	public Optional<Filme> buscarPorId(Long filmeId) {
		return filmeRepository.findById(filmeId);
	}
	
	public void excluir(Long filmeId) {
		filmeRepository.deleteById(filmeId);
	}
}
