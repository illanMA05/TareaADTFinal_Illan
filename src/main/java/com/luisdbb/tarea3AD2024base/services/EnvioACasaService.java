package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.config.ConexionObjectDB;
import com.luisdbb.tarea3AD2024base.modelo.Direccion;
import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Service
public class EnvioACasaService {

	EntityManager em = ConexionObjectDB.em;
	
	public List<EnvioACasa> findAllEnvios(){
		TypedQuery<EnvioACasa> q1 = em.createQuery("SELECT e FROM EnvioACasa e", EnvioACasa.class);

		return  q1.getResultList();
	}
	
	public List<Direccion> findAllDirecciones(){
		TypedQuery<Direccion> q1 = em.createQuery("SELECT e.direccion FROM EnvioACasa e", Direccion.class);

		return  q1.getResultList();
	}
	
}
