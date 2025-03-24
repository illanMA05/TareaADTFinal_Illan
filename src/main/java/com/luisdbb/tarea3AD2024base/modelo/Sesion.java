package com.luisdbb.tarea3AD2024base.modelo;

public class Sesion {
	
	private String usuario;

	public Sesion(String usuario) {
		super();
		this.usuario = usuario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public static Sesion  sesion = null;

}
