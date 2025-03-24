package com.luisdbb.tarea3AD2024base.modelo;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class EnvioACasa  extends Servicio{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private double peso;
	private int[] volumen = new int[3];
	private boolean urgente = false;
	private Long idParada;
	
	@Embedded
	private Direccion direccion;
	
	//constructores
	

	public EnvioACasa(Long id, String nombre, double precio, List<Long> paradas, List<Long> contratar, Long id2,
			double peso, int[] volumen, boolean urgente) {
		super(id, nombre, precio, paradas, contratar);
		id = id2;
		this.peso = peso;
		this.volumen = volumen;
		this.urgente = urgente;
	}

	public EnvioACasa(Long id, String nombre, double precio, List<Long> paradas, List<Long> contratar, Long id2,
			double peso, int[] volumen, boolean urgente, Long idParada, Direccion direccion) {
		super(id, nombre, precio, paradas, contratar);
		id = id2;
		this.peso = peso;
		this.volumen = volumen;
		this.urgente = urgente;
		this.idParada = idParada;
		this.direccion = direccion;
	}

	public EnvioACasa(Long id, String nombre, double precio, List<Long> paradas, List<Long> contratar) {
		super(id, nombre, precio, paradas, contratar);
	}
	
	

	
	public EnvioACasa(Long id, String nombre, double precio, List<Long> paradas, List<Long> contratar, double peso,
			int[] volumen, boolean urgente) {
		super(id, nombre, precio, paradas, contratar);
		this.peso = peso;
		this.volumen = volumen;
		this.urgente = urgente;
	}
	

	public EnvioACasa(Long id, String nombre, double precio, List<Long> paradas, List<Long> contratar, Long id2,
			double peso, int[] volumen, boolean urgente, Direccion direccion) {
		super(id, nombre, precio, paradas, contratar);
		id = id2;
		this.peso = peso;
		this.volumen = volumen;
		this.urgente = urgente;
		this.direccion = direccion;
	}

	public EnvioACasa(long id2, String nombre, double precio, Object paradas, Object contratar, double peso2,
			int[] volumen2, boolean selected, Long idPa) {
		// TODO Auto-generated constructor stub
		super(id2, nombre, precio, paradas, contratar);
		this.peso = peso2;
		this.volumen = volumen2;
		this.urgente = selected;
		this.idParada = idPa;
	}

	//getters y setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}


	public int[] getVolumen() {
		return volumen;
	}

	public void setVolumen(int[] volumen) {
		this.volumen = volumen;
	}

	public boolean isUrgente() {
		return urgente;
	}

	public void setUrgente(boolean urgente) {
		this.urgente = urgente;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}
	
	public Long getIdParada() {
		return idParada;
	}

	public void setIdParada(Long idParada) {
		this.idParada = idParada;
	}

	
	//metodos



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(volumen);
		result = prime * result + Objects.hash(direccion, id, idParada, peso, urgente);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnvioACasa other = (EnvioACasa) obj;
		return Objects.equals(direccion, other.direccion) && Objects.equals(id, other.id)
				&& Objects.equals(idParada, other.idParada)
				&& Double.doubleToLongBits(peso) == Double.doubleToLongBits(other.peso) && urgente == other.urgente
				&& Arrays.equals(volumen, other.volumen);
	}

	@Override
	public String toString() {
		return "EnvioACasa [id=" + id + ", peso=" + peso + ", volumen=" + Arrays.toString(volumen) + ", urgente="
				+ urgente + ", idParada=" + idParada + ", direccion=" + direccion + "]";
	}
	

	
	
	
}
