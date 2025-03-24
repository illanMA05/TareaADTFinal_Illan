package com.luisdbb.tarea3AD2024base.services;

import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.repositorios.CarnetRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarnetService {
	
	@Autowired
	private CarnetRepository carnetRepository;
	
	public Carnet save(Carnet entity) {
		return carnetRepository.save(entity);
	}

	@Autowired
	private EntityManager entityManager;
	
	public Carnet findByPere(Peregrino entity) {
		return carnetRepository.findByIdPere(entity);
	}
	
	@Transactional
    public Carnet actualizarEntidad(Carnet entidad) {
        return entityManager.merge(entidad);
    }
}
