package com.felipesouza.locadora.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.felipesouza.locadora.domain.exception.NegocioException;
import com.felipesouza.locadora.domain.model.Filme;
import com.felipesouza.locadora.domain.model.Usuario;
import com.felipesouza.locadora.domain.service.CadastroFilmeService;
import com.felipesouza.locadora.domain.service.CadastroUsuarioService;

@RestController
	class FilmeController {
	

	@Autowired
	CadastroFilmeService cadastroFilme;
	
	@Autowired
	CadastroUsuarioService cadastroUsuario;
	

	@RequestMapping(value = "/filmes", method = RequestMethod.GET)
	public List<Filme> listar() {
		return cadastroFilme.listar();
	}
	
	@RequestMapping(value = "/filmes", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Filme adicionar(@Valid @RequestBody Filme filme) {
		return cadastroFilme.salvar(filme);
	}

	@RequestMapping(value = "/filmes/disponivel", method = RequestMethod.GET)
	public List<Filme>	listarDisponiveis(){
		 List<Filme> retorno = cadastroFilme.listarDisponiveis();
		 if(retorno.isEmpty())
			 throw new NegocioException("Nenhum filme disponível encontrado.");
		 return retorno;
	}

	
	@RequestMapping(value = "/filmes/titulo/{titulo}", method = RequestMethod.GET)
	public ResponseEntity<List<Filme>> buscarPorTitulo(@PathVariable  String titulo ){
		 List<Filme> retorno =  cadastroFilme.listarPorTitulo(titulo);
		 if(retorno.isEmpty())
			 return ResponseEntity.notFound().build();
		
		 return ResponseEntity.ok(retorno);
	}
	
	@RequestMapping(value = "/filmes/alugar", method = RequestMethod.GET)
	@ResponseBody
	public String alugar(@RequestParam(name = "filmeId") Long filmeId, @RequestParam(name = "email") String email) {
		
		if(filmeId == null)
			throw new NegocioException("É obrigatório informar o id do filme a ser locado");
		if (email.isEmpty())
			throw new NegocioException("É obrigatório informar o e-mail do usuário que locara o filme");
			
		
		Usuario usuario = cadastroUsuario.buscarPorEmail(email);
		
		if (usuario == null) {
			throw new NegocioException("usuário [" + email + "] não encontrado");
		}
		
		Optional<Filme> filme = cadastroFilme.buscarPorId(filmeId);
		
		if (!filme.isPresent()) {
			throw new NegocioException("Filme não encontrado.");
		}
		
		if(filme.get().getAlocadores().size() == filme.get().getCopias())
			throw new NegocioException("Filme não está disponivel.");
		
		usuario.alugar(filme.get());
		
		usuario = cadastroUsuario.salvarUsuario(usuario);
		
		return "Filme '" + filme.get().getTitulo() + "' foi locado";
	}
	
	@RequestMapping(value = "/filmes/devolver", method = RequestMethod.GET)
	@ResponseBody
	public String devolver(@RequestParam(name = "filmeId") Long filmeId, @RequestParam(name = "email") String email) {
		
		if(filmeId == null)
			throw new NegocioException("É obrigatório informar o id do filme a ser devolvido.");
		if (email.isEmpty())
			throw new NegocioException("É obrigatório informar o e-mail do usuário que devolvera o filme.");
		
		Usuario usuario = cadastroUsuario.buscarPorEmail(email);
			
		if (usuario == null) {
			throw new NegocioException("Usuário [" + email + "] não encontrado");
		}
		
		Optional<Filme> filme = cadastroFilme.buscarPorId(filmeId);
		
		if (!filme.isPresent()) {
			throw new NegocioException("Filme não encontrado.");
		}
		
		if(!usuario.getFilmesLocados().contains(filme.get())) {
			throw new NegocioException("Filme não esta locado por usuário.");
		}else {
			usuario.devolver(filme.get());
			usuario = cadastroUsuario.salvarUsuario(usuario);
			return "Filme '" + filme.get().getTitulo() + "' devolvido";
		}
			
	}
}