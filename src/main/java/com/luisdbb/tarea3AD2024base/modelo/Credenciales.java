package com.luisdbb.tarea3AD2024base.modelo;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Credenciales")
public class Credenciales {

	@Id
	@Column(name = "nombreUsuario", updatable = false, nullable = false)
	private String nombre;
	
	@Column
	private String contraseña;
	
	@Column(name = "perfil", updatable = false, nullable = false)
	private String perfil;

	@OneToOne
	@JoinColumn(name="id_Pere")
	private Peregrino pere;
	
	@OneToOne
	@JoinColumn(name="id_Parada")
	private Paradas parada;
	
	//constructores
	
	public Credenciales(String nombre, String contraseña, String perfil) {
		super();
		this.nombre = nombre;
		this.contraseña = contraseña;
		this.perfil = perfil;
	}
	
	public Credenciales () {}

	//getters y setters
	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public Peregrino getPere() {
		return pere;
	}

	public void setPere(Peregrino pere) {
		this.pere = pere;
	}

	public Paradas getParada() {
		return parada;
	}

	public void setParada(Paradas parada) {
		this.parada = parada;
	}
	
	//metodos
	
	

	@Override
	public int hashCode() {
		return Objects.hash(contraseña, nombre, perfil);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Credenciales other = (Credenciales) obj;
		return Objects.equals(contraseña, other.contraseña) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(perfil, other.perfil);
	}

	@Override
	public String toString() {
		return "Credenciales [nombre=" + nombre + ", contraseña=" + contraseña + ", perfil=" + perfil + "]";
	}
	
	
	

}
