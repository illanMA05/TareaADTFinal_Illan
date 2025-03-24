package com.luisdbb.tarea3AD2024base.modelo;

import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConjuntoContratado {
	
	private Long id;
	private double precioTotal;
	private char modoPago;
	private String extra = null;
	private Long estancia;
	private List<Long> servicios = new ArrayList<Long>();
	
	//constructores
	public ConjuntoContratado(Long id, double precioTotal, char modoPago, String extra, Long estancia,
			List<Long> contratar) {
		super();
		this.id = id;
		this.precioTotal = precioTotal;
		this.modoPago = modoPago;
		this.extra = extra;
		this.estancia = estancia;
		this.servicios = contratar;
	}
	public ConjuntoContratado() {}
	
	//getters y setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getPrecioTotal() {
		return precioTotal;
	}
	public void setPrecioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
	}
	public char getModoPago() {
		return modoPago;
	}
	public void setModoPago(char modoPago) {
		this.modoPago = modoPago;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	public Long getEstancia() {
		return estancia;
	}
	public void setEstancia(Long estancia) {
		this.estancia = estancia;
	}
	public List<Long> getContratar() {
		return servicios;
	}
	public void setContratar(List<Long> contratar) {
		this.servicios = contratar;
	}
	
	
	//metodos

	@Override
	public int hashCode() {
		return Objects.hash(servicios, estancia, extra, id, modoPago, precioTotal);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConjuntoContratado other = (ConjuntoContratado) obj;
		return Objects.equals(servicios, other.servicios) && Objects.equals(estancia, other.estancia)
				&& Objects.equals(extra, other.extra) && Objects.equals(id, other.id) && modoPago == other.modoPago
				&& Double.doubleToLongBits(precioTotal) == Double.doubleToLongBits(other.precioTotal);
	}
	@Override
	public String toString() {
		return "ConjuntoContratado [id=" + id + ", precioTotal=" + precioTotal + ", modoPago=" + modoPago + ", extra="
				+ extra + ", estancia=" + estancia + ", contratar=" + servicios + "]";
	}
	
	
	
	
	
}
