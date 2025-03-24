package com.luisdbb.tarea3AD2024base.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.luisdbb.tarea3AD2024base.modelo.Estancias;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.repositorios.EstanciasRepository;

@Service
public class EstanciasService {

	@Autowired
	private EstanciasRepository estanciasRepository;
	
	public Estancias save (Estancias entity) {
		return estanciasRepository.save(entity);
	}
	
	public List<Estancias> findByIdPere(Peregrino p) {
		return estanciasRepository.findByPeregrinoEEquals(p);
	}
	
	public Estancias findByIdE(Long id) {
		return estanciasRepository.findByIdE(id);
	}
}
