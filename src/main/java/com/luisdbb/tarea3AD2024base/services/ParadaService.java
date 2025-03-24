package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Paradas;
import com.luisdbb.tarea3AD2024base.repositorios.ParadaRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class ParadaService {
	
	@Autowired
	private ParadaRepository paradaRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	@Transactional
    public Paradas actualizarEntidad(Paradas entidad) {
        return entityManager.merge(entidad);
    }
	
	public List<Paradas> findAll() {
		return paradaRepository.findAll();
	}
	
	public Paradas findByNombre(String nombre) {
		return paradaRepository.findByNombre(nombre);
	}
	
	public Paradas findByResponsable(String nombre) {
		return paradaRepository.findByResponsable(nombre);
	}
	
	public Paradas findByIdPa(Long id) {
		return paradaRepository.findByIdPa(id);
	}

	public Paradas save(Paradas entity) {
		return paradaRepository.save(entity);
	}

	
	public boolean ParadaExistePorNombre(String nombre) {
		Paradas creden = paradaRepository.findByNombre(nombre);
		if(creden == null) 
			return false;
		else
			return true;	
	}
	
	
}
