package com.felipesouza.locadora.domain.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.felipesouza.locadora.domain.model.Filme;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long>{

	Filme findByTitulo(String titulo);
	
	List<Filme> findByTituloContaining(String titulo);
	
	@Query(value = "SELECT * FROM FILME WHERE FILME.ID IN (SELECT filmes.id FROM (SELECT id, copias FROM filme) as filmes, (SELECT filme_id, COUNT(*) qtd FROM locacao GROUP BY filme_id) as  conta WHERE filmes.copias > conta.qtd) or id not in (SELECT filme_id from locacao)",
	nativeQuery = true)
	List<Filme> findAllDisponivel();	
}
