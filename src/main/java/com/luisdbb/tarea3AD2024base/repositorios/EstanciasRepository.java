package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Estancias;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;

@Repository
public interface EstanciasRepository extends JpaRepository<Estancias,Long>{

	List <Estancias> findByPeregrinoEEquals(Peregrino p);
	
	Estancias findByIdE(Long id);
}
