package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.luisdbb.tarea3AD2024base.config.DataConnection;
import com.luisdbb.tarea3AD2024base.modelo.ConjuntoContratado;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;

@Service
public class Servicios_ConjContraService {
	
	private ObjectContainer db = DataConnection.getInstance();
	
	public List<Servicio> findAllServicios(){
		
		List<Servicio> ser = null;
		ser = db.query(Servicio.class);
		return ser;
	}
	
public List<ConjuntoContratado> findAllConjuntos(){
		
		List<ConjuntoContratado> ser = null;
		ser = db.query(ConjuntoContratado.class);
		return ser;
	}
	


	public Boolean findServiceByNombre( String nombre) {
		
		Servicio s = new Servicio(null, nombre,null, null, null);
		
		ObjectSet <Servicio>ter2 = db.queryByExample(s);
			
		boolean ret=false;
		for(Servicio ser: ter2) {
			if(ser.getNombre().equals(nombre)) ret=true;
			
		}
		return ret;
	}
}
