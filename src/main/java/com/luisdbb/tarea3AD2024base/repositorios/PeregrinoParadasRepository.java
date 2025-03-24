package com.luisdbb.tarea3AD2024base.repositorios;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Paradas;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.PeregrinoParadas;

@Repository
public interface PeregrinoParadasRepository extends JpaRepository<PeregrinoParadas, Long>{
	
	List <PeregrinoParadas> findByParadasEquals(Paradas p);
	
	List <PeregrinoParadas> findByFechaIsBetween(LocalDate fechaMe, LocalDate fechaMa);
	
	List <PeregrinoParadas> findByFechaIsBetweenAndParadasEquals(LocalDate fechaMe, LocalDate fechaMa,Paradas p);

	List<PeregrinoParadas> findByPeregrinoEquals(Peregrino p);
}
