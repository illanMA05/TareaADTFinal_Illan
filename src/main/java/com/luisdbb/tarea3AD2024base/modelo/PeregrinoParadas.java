package com.luisdbb.tarea3AD2024base.modelo;



import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
import java.util.Objects;
import jakarta.persistence.Column;

@Entity
@Table(name="PeregrinoParada")
public class PeregrinoParadas {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	
	@ManyToOne()
	@JoinColumn(name = "parada_Id")
	private Paradas paradas;
	
	@ManyToOne()
	@JoinColumn(name = "peregrino_Id")
	private Peregrino peregrino;
	
	@Column
	private LocalDate fecha;
	//constructor

	public PeregrinoParadas(Long id, Paradas paradas, Peregrino peregrino) {
		super();
		this.id = id;
		this.paradas = paradas;
		this.peregrino = peregrino;
	}
	
	public PeregrinoParadas(Paradas paradas, Peregrino peregrino, LocalDate fecha) {
		super();
		
		this.paradas = paradas;
		this.peregrino = peregrino;
		this.fecha = fecha;
	}
	
	public PeregrinoParadas( LocalDate fecha) {
		super();
		this.fecha = fecha;
	}

	public PeregrinoParadas(Long id, Paradas paradas, Peregrino peregrino, LocalDate fecha) {
		super();
		this.id = id;
		this.paradas = paradas;
		this.peregrino = peregrino;
		this.fecha = fecha;
	}



	public PeregrinoParadas() {
		
	}
	
	

	//getters y setters

	public LocalDate getFecha() {
		return fecha;
	}



	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}


	public Long getId() {
		return id;
	}

	public Paradas getParadas() {
		return paradas;
	}

	public void setParadas(Paradas paradas) {
		this.paradas = paradas;
	}

	public Peregrino getPeregrino() {
		return peregrino;
	}

	public void setPeregrino(Peregrino peregrino) {
		this.peregrino = peregrino;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fecha, id, paradas, peregrino);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PeregrinoParadas other = (PeregrinoParadas) obj;
		return Objects.equals(fecha, other.fecha) && Objects.equals(id, other.id)
				&& Objects.equals(paradas, other.paradas) && Objects.equals(peregrino, other.peregrino);
	}

	@Override
	public String toString() {
		return "PeregrinoParadas [id=" + id + ", paradas=" + paradas + ", peregrino=" + peregrino + ", fecha=" + fecha
				+ "]";
	}
	
	
	
	
	
}
