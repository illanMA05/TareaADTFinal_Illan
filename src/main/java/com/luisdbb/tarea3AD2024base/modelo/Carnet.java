package com.luisdbb.tarea3AD2024base.modelo;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.*;

@Entity
@Table(name="Carnet")
public class Carnet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long idC;
	
	@Column
	private LocalDate fechaExp = LocalDate.now();
	
	@Column
	private double distancia=0.0;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="paradaInicio")
	private Paradas inicio;
	
	@Column
	private int nvips=0;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(unique = true)
	private Peregrino idPere;

	//constructores
	
	public Carnet(Long idC, LocalDate fechaExp, double distancia, int nvips, Peregrino idPere) {
		super();
		this.idC = idC;
		this.fechaExp = fechaExp;
		this.distancia = distancia;
		this.nvips = nvips;
		this.idPere = idPere;
	}
	public Carnet (double distancia, LocalDate fechaExp, int nvips, Peregrino idPere, Paradas inicio ) {
		this.distancia=distancia;
		this.fechaExp=fechaExp;
		this.nvips=nvips;
		this.idPere=idPere;
		this.inicio=inicio;
	}

public Carnet (double distancia, LocalDate fechaExp, int nvips,Paradas inicio ) {
	this.distancia=distancia;
	this.fechaExp=fechaExp;
	this.nvips=nvips;
	this.inicio=inicio;
}

public Carnet (double distancia, LocalDate fechaExp, int nvips) {
	this.distancia=distancia;
	this.fechaExp=fechaExp;
	this.nvips=nvips;

}
	
	public Carnet() {}

	
	//getters y settesr
	
	public Long getIdC() {
		return idC;
	}

	public void setIdC(Long idC) {
		this.idC = idC;
	}

	public LocalDate getFechaExp() {
		return fechaExp;
	}

	public void setFechaExp(LocalDate fechaExp) {
		this.fechaExp = fechaExp;
	}

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

	public int getNvips() {
		return nvips;
	}

	public void setNvips(int nvips) {
		this.nvips = nvips;
	}

	public Peregrino getIdPere() {
		return idPere;
	}

	public void setIdPere(Peregrino idPere) {
		this.idPere = idPere;
	}

	public Paradas getInicio() {
		return inicio;
	}
	public void setInicio(Paradas inicio) {
		this.inicio = inicio;
	}
	
	//metodos
	
	
	@Override
	public int hashCode() {
		return Objects.hash(distancia, fechaExp, idC, idPere, inicio, nvips);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carnet other = (Carnet) obj;
		return Double.doubleToLongBits(distancia) == Double.doubleToLongBits(other.distancia)
				&& Objects.equals(fechaExp, other.fechaExp) && Objects.equals(idC, other.idC)
				&& Objects.equals(idPere, other.idPere) && Objects.equals(inicio, other.inicio) && nvips == other.nvips;
	}

	@Override
	public String toString() {
		return "Carnet [idC=" + idC + ", fechaExp=" + fechaExp + ", distancia=" + distancia + ", inicio=" + inicio
				+ ", nvips=" + nvips + ", idPere=" + idPere + "]";
	}
	
	
	
}
