package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Paradas;

@Repository
public interface ParadaRepository extends JpaRepository <Paradas, Long>{
	
	Paradas findByIdPa(Long id);
	
	List <Paradas> findAll();
	
	Paradas findByNombre(String nombre);
	
	Paradas findByResponsable(String responsable);

}
