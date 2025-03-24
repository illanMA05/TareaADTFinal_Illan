package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;

@Repository
public interface EnvioACasaRepository {

	
	List<EnvioACasa> findAllEnvios();
}
