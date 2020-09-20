package com.felipesouza.locadora.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.felipesouza.locadora.domain.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	Role  findByNome(String nome);
}
