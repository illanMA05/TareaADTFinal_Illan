package com.luisdbb.tarea3AD2024base.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name="Paradas")
public class Paradas {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long idPa;
	
	@Column
	private String nombre;
	
	@Column
	private char region;
	
	@Column
	private String responsable;
	
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "parada_Id")
	public List<PeregrinoParadas> perePara = new ArrayList<PeregrinoParadas>();
	
	@OneToMany
	@JoinColumn(name = "paradaInicio")
	private List<Carnet> carnet = new ArrayList<Carnet>();
	
	
	@OneToMany(mappedBy="paradaE",fetch = FetchType.EAGER )
	private List<Estancias> estancias = new ArrayList<Estancias>();
	
	@OneToOne(mappedBy="parada", cascade=CascadeType.ALL)
	private Credenciales credenciales;
	
	//constructores
	
	public Paradas() {
	}


	public Paradas(Long idPa, String nombre, char region, String responsable, List<PeregrinoParadas> perePara) {
		super();
		this.idPa = idPa;
		this.nombre = nombre;
		this.region = region;
		this.responsable = responsable;
		this.perePara = perePara;
	}

	public Paradas(Long idPa, String nombre, char region, String responsable) {
		super();
		this.idPa = idPa;
		this.nombre = nombre;
		this.region = region;
		this.responsable = responsable;
	}
	
	public Paradas(String nombre, char region, String responsable) {
		super();
		this.nombre = nombre;
		this.region = region;
		this.responsable = responsable;
	}


	
	//getters y setters
	

	public Long getIdPa() {
		return idPa;
	}

	public void setIdPa(Long idPa) {
		this.idPa = idPa;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public char getRegion() {
		return region;
	}

	public void setRegion(char region) {
		this.region = region;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}



//	public Carnet getCarnet() {
//		return carnet;
//	}
//
//	public void setCarnet(Carnet carnet) {
//		this.carnet = carnet;
//	}

	public List<Estancias> getEstancias() {
		return estancias;
	}

	public void setEstancias(List<Estancias> estancias) {
		this.estancias = estancias;
	}

	public Credenciales getCredenciales() {
		return credenciales;
	}

	public void setCredenciales(Credenciales credenciales) {
		this.credenciales = credenciales;
	}

	//metodods
	
	

	@Override
	public int hashCode() {
		return Objects.hash( credenciales, estancias, idPa, nombre, perePara, region, responsable);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Paradas other = (Paradas) obj;
		return  Objects.equals(credenciales, other.credenciales)
				&& Objects.equals(estancias, other.estancias) && Objects.equals(idPa, other.idPa)
				&& Objects.equals(nombre, other.nombre) && Objects.equals(perePara, other.perePara)
				&& region == other.region && Objects.equals(responsable, other.responsable);
	}

	@Override
	public String toString() {
		return "Paradas [idPa=" + idPa + ", nombre=" + nombre + ", region=" + region + ", responsable=" + responsable
				+ ", perePara=" + perePara + ", estancias=" + estancias + ", credenciales="
				+ credenciales + "]";
	}
	
	
	

}
