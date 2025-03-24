package com.luisdbb.tarea3AD2024base.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name="Peregrinos")
public class Peregrino implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long idP;
	
	@Column
	private String nombre;
	
	@Column
	private String nacionalidad;
	
	@OneToMany(mappedBy="peregrinoE",fetch=FetchType.EAGER)
	public List<Estancias> estancias = new ArrayList<Estancias>();
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "peregrino_Id")
	public List<PeregrinoParadas> perePara = new ArrayList<PeregrinoParadas>();
	
	@OneToOne(mappedBy = "idPere", cascade=CascadeType.ALL)
	private Carnet carnet;
	
	@OneToOne(mappedBy="pere", cascade=CascadeType.ALL)
	private Credenciales creden;
	
	@Column
	private String nombreUsuario;
	
	@Column
	private String email;

	
	//constructores

	

	public Peregrino(Long idP, String nombre, String nacionalidad, List<PeregrinoParadas> perePara) {
		super();
		this.idP = idP;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.perePara = perePara;
	}

	public Peregrino(String nacionalidad, String nombre, String nombreUsuario, String email) {
		this.nacionalidad=nacionalidad;
		this.nombre=nombre;
		this.nombreUsuario=nombreUsuario;
		this.email= email;
	}
	
	public Peregrino(Long idP, String nombre, String nacionalidad, String nombreUsuaio, String email) {
		super();
		this.idP = idP;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.nombreUsuario = nombreUsuaio;
		this.email = email;
	}


	

	public Peregrino(Long idP, String nombre, String nacionalidad, List<Estancias> estancias,
			List<PeregrinoParadas> perePara) {
		super();
		this.idP = idP;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.estancias = estancias;
		this.perePara = perePara;
	}

	public Peregrino(Long idP, String nombre, String nacionalidad, String nombreUsuaio) {
		super();
		this.idP = idP;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.nombreUsuario = nombreUsuaio;
	}



	public Peregrino(Long idP, String nombre, String nacionalidad, List<Estancias> estancias,
			List<PeregrinoParadas> perePara, Carnet carnet) {
		super();
		this.idP = idP;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.estancias = estancias;
		this.perePara = perePara;
		this.carnet = carnet;
	}

	public Peregrino() {}

	
	//gettesr y setters
	
	
	
	public Long getIdP() {
		return idP;
	}

	public List<Estancias> getEstancias() {
		return estancias;
	}



	public void setEstancias(List<Estancias> estancias) {
		this.estancias = estancias;
	}



	public Carnet getCarnet() {
		return carnet;
	}



	public void setCarnet(Carnet carnet) {
		this.carnet = carnet;
	}



	public Credenciales getCreden() {
		return creden;
	}



	public void setCreden(Credenciales creden) {
		this.creden = creden;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}

	public void setIdP(Long idP) {
		this.idP = idP;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}



	public List<PeregrinoParadas> getPerePara() {
		return perePara;
	}

	public void setPerePara(List<PeregrinoParadas> perePara) {
		this.perePara = perePara;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	
	//metodso
	
	

	@Override
	public int hashCode() {
		return Objects.hash(carnet, creden, email, estancias, idP, nacionalidad, nombre, nombreUsuario, perePara);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Peregrino other = (Peregrino) obj;
		return Objects.equals(carnet, other.carnet) && Objects.equals(creden, other.creden)
				&& Objects.equals(email, other.email) && Objects.equals(estancias, other.estancias)
				&& Objects.equals(idP, other.idP) && Objects.equals(nacionalidad, other.nacionalidad)
				&& Objects.equals(nombre, other.nombre) && Objects.equals(nombreUsuario, other.nombreUsuario)
				&& Objects.equals(perePara, other.perePara);
	}

	@Override
	public String toString() {
		return "Peregrino [idP=" + idP + ", nombre=" + nombre + ", nacionalidad=" + nacionalidad + ", estancias="
				+ estancias + ", perePara=" + perePara + ", carnet=" + carnet + ", creden=" + creden
				+ ", nombreUsuario=" + nombreUsuario + ", email=" + email + "]";
	}
	
	
	
	
	
}
