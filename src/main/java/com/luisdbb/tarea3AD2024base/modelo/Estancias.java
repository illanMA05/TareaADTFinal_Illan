package com.luisdbb.tarea3AD2024base.modelo;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Estancias")
public class Estancias {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long idE;
	
	@Column
	private LocalDate fecha;
	
	@Column
	private boolean vip= false;
	
	@ManyToOne
	@JoinColumn(name = "pere_Id")
	private Peregrino peregrinoE;
	
	@ManyToOne
	@JoinColumn(name = "parada_Id")
	private Paradas paradaE;

	//constructores
	public Estancias(Long idE, LocalDate fecha, boolean vip, Peregrino peregrinoE, Paradas paradaE) {
		super();
		this.idE = idE;
		this.fecha = fecha;
		this.vip = vip;
		this.peregrinoE = peregrinoE;
		this.paradaE = paradaE;
	}
	
	
	
	public Estancias(LocalDate fecha, boolean vip) {
		super();
		this.fecha = fecha;
		this.vip = vip;
	}



	public Estancias() {}

	//getters y setters
	
	public Long getIdE() {
		return idE;
	}

	public void setIdE(Long idE) {
		this.idE = idE;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public boolean isVip() {
		return vip;
	}

	public void setVip(boolean vip) {
		this.vip = vip;
	}

	public Peregrino getPeregrinoE() {
		return peregrinoE;
	}

	public void setPeregrinoE(Peregrino peregrinoE) {
		this.peregrinoE = peregrinoE;
	}

	public Paradas getParadaE() {
		return paradaE;
	}

	public void setParadaE(Paradas paradaE) {
		this.paradaE = paradaE;
	}

	
	//metodos
	
	@Override
	public int hashCode() {
		return Objects.hash(fecha, idE, paradaE, peregrinoE, vip);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estancias other = (Estancias) obj;
		return Objects.equals(fecha, other.fecha) && Objects.equals(idE, other.idE)
				&& Objects.equals(paradaE, other.paradaE) && Objects.equals(peregrinoE, other.peregrinoE)
				&& vip == other.vip;
	}

	@Override
	public String toString() {
		return "Estancias [idE=" + idE + ", fecha=" + fecha + ", vip=" + vip + ", peregrinoE=" + peregrinoE
				+ ", paradaE=" + paradaE + "]";
	}
	
	
}
