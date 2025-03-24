package com.luisdbb.tarea3AD2024base.services;

import java.time.LocalDate;
import java.util.List;

import com.luisdbb.tarea3AD2024base.modelo.Paradas;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.PeregrinoParadas;
import com.luisdbb.tarea3AD2024base.repositorios.PeregrinoParadasRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeregrinoParadaService {
	
	@Autowired
	private PeregrinoParadasRepository ppRepository;
	
	public List<PeregrinoParadas> findByParadasEquals(Paradas p){
		return ppRepository.findByParadasEquals(p);
	}
	
	public List<PeregrinoParadas> findByPeregrinoEquals(Peregrino p){
		return ppRepository.findByPeregrinoEquals(p);
	}
	
	public List<PeregrinoParadas> findByFechaIsBetween(LocalDate f1, LocalDate f2){
		return ppRepository.findByFechaIsBetween(f1, f2);
	}
	
	public List<PeregrinoParadas> findByFechaIsBetweenAndParadasEquals(LocalDate f1, LocalDate f2,Paradas p){
		
		return ppRepository.findByFechaIsBetweenAndParadasEquals(f1, f2,p);
	}
	
	public PeregrinoParadas save(PeregrinoParadas entity) {
		return ppRepository.save(entity);
	}
	
	@Autowired
	private EntityManager entityManager;
	
	@Transactional
    public PeregrinoParadas actualizarEntidad(PeregrinoParadas entidad) {
        return entityManager.merge(entidad);
    }
	

}
