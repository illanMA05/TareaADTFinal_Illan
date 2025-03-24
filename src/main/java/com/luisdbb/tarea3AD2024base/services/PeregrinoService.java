package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.repositorios.PeregrinoRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class PeregrinoService {
	
	@Autowired
	private PeregrinoRepository peregrinoRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	@Transactional
    public Peregrino actualizarEntidad(Peregrino entidad) {
        return entityManager.merge(entidad);
    }
	
	public List<Peregrino> findAll(){
		return peregrinoRepository.findAll();
	}
	
	public Peregrino save(Peregrino entity) {
		return peregrinoRepository.save(entity);
	}
	
	public Peregrino findById(Long id) {
		return peregrinoRepository.findByIdP(id);
	}
	
	
	public Peregrino findByNombre(String nombre) {
		return peregrinoRepository.findByNombre(nombre);
	}
	
	public Peregrino findByNombreUsu(String nombre) {
		return peregrinoRepository.findByNombreUsuario(nombre);
	}
	
	
	public boolean peregrinoExiste(String nombre) {
		Peregrino pere = peregrinoRepository.findByNombreUsuario(nombre);
		if(pere==null)
			return false;
		else
			return true;
	}


}
