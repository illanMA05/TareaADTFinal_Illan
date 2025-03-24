package com.luisdbb.tarea3AD2024base.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Credenciales;
import com.luisdbb.tarea3AD2024base.repositorios.CredencialesRepository;


@Service
public class CredencialesService {

	@Autowired
	private CredencialesRepository credencialesRepository;
	
	public Credenciales Save(Credenciales entity) {
		return credencialesRepository.save(entity);
	}
	
	public Credenciales findByNombre(String nombre) {
		return credencialesRepository.findByNombre(nombre);
	}
	
	public boolean CredencialesExisten(String nombre, String contraseña) {
		Credenciales creden = credencialesRepository.findByNombre(nombre);
		if(creden == null) {
			return false;
		}
			
		else {
			if(contraseña.equals(creden.getContraseña()))
				return true;
			else
				return false;
		}	
	}
	
	
	public boolean CredencialesExistenPorNombre(String nombre) {
		Credenciales creden = credencialesRepository.findByNombre(nombre);
		if(creden == null) 
			return false;
		else
			return true;
			
		
	}
	
	
	public int perfilCredenciales(String nombre) {
		Credenciales creden = credencialesRepository.findByNombre(nombre);
		if(creden.getPerfil().equals("peregrino"))
			return 0;
		else
			if(creden.getPerfil().equals("parada")) {
				return 1;
				}
			else
				return 2;
	}
	
}
