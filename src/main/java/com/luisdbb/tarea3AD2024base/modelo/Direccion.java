package com.luisdbb.tarea3AD2024base.modelo;

import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Embeddable
public class Direccion {
	
	private Long idEnvio;
	private String direcciones;
	private String localidad;
	
	//constructores
	public Direccion(Long id, String direccion, String localidad) {
		super();
		this.idEnvio = id;
		this.direcciones = direccion;
		this.localidad = localidad;
	}
	


	public Direccion() {}

	//getters y setters
	
	public Long getId() {
		return idEnvio;
	}

	public void setId(Long id) {
		this.idEnvio = id;
	}

	public String getDireccion() {
		return direcciones;
	}

	public void setDireccion(String direccion) {
		this.direcciones = direccion;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	
	//metodos
	
	
	@Override
	public int hashCode() {
		return Objects.hash(direcciones, idEnvio, localidad);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Direccion other = (Direccion) obj;
		return Objects.equals(direcciones, other.direcciones) && Objects.equals(idEnvio, other.idEnvio)
				&& Objects.equals(localidad, other.localidad);
	}

	@Override
	public String toString() {
		return "Direccion [id=" + idEnvio + ", direccion=" + direcciones + ", localidad=" + localidad + "]";
	}
	
}
