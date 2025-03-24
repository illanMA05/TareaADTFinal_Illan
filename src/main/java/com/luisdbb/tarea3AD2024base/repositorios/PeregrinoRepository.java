package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Peregrino;

@Repository
public interface PeregrinoRepository extends JpaRepository<Peregrino, Long>{

	
	Peregrino findByIdP(Long id);
	
	Peregrino findByNombre(String nombre);
	
	Peregrino findByNombreUsuario(String nombreUsu);
	
	List<Peregrino> findAll();
	
	
}
