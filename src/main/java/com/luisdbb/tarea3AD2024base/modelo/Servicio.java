package com.luisdbb.tarea3AD2024base.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Servicio {
	
	private Long id;
	private String nombre;
	private double precio;
	private List<Long> paradas = new ArrayList<Long>();
	private List<Long> conjContratado = new ArrayList<Long>();
	
	//cosntructores
	
	
	public Servicio(Long id, String nombre, double precio, List<Long> paradas, List<Long> contratar) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.paradas = paradas;
		this.conjContratado = contratar;
	}
	
	
	
	public Servicio(String nombre, double precio, List<Long> paradas) {
		super();
		this.nombre = nombre;
		this.precio = precio;
		this.paradas = paradas;
	}



	public Servicio () {}

	public Servicio(Object id2, String nombre2, Object object, Object paradas2, Object contratar2) {

	}


	public Servicio(Long id, String nombre, double precio, List<Long> paradas) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.paradas = paradas;
	}



	//settesr y getters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public List<Long> getParadas() {
		return paradas;
	}

	public void setParadas(List<Long> paradas) {
		this.paradas = paradas;
	}

	public List<Long> getContratar() {
		return conjContratado;
	}

	public void setContratar(List<Long> contratar) {
		this.conjContratado = contratar;
	}

	//metodos
	
	@Override
	public int hashCode() {
		return Objects.hash(conjContratado, id, nombre, paradas, precio);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Servicio other = (Servicio) obj;
		return Objects.equals(conjContratado, other.conjContratado) && Objects.equals(id, other.id)
				&& Objects.equals(nombre, other.nombre) && Objects.equals(paradas, other.paradas)
				&& Double.doubleToLongBits(precio) == Double.doubleToLongBits(other.precio);
	}

	@Override
	public String toString() {
		return "Servicio [id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", paradas=" + paradas
				+ ", contratar=" + conjContratado + "]";
	}
	
	

	
	
}
