package com.luisdbb.tarea3AD2024base.modelo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Paises {
	
	public static HashMap<String, String> mapaPaises = new HashMap<String, String>();
	public static List<String> listaPaises = new ArrayList<String>();
	
	private String id;
	private String nombre;
	
	//constructor
	public Paises(String id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	
//setters y getters
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

//hashCode y Equals


	@Override
	public int hashCode() {
		return Objects.hash(id, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Paises other = (Paises) obj;
		return Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre);
	}


	//metodos
	
	//toString
	

	@Override
	public String toString() {
		return "Paises [id=" + id + ", nombre=" + nombre + "]";
	}

	
	
	//leer documento paises.xml;
	public static void leerPaises(){
		
		
		try {
			
			 DocumentBuilderFactory fabricaConstructorDocumento = DocumentBuilderFactory.newInstance();
	            DocumentBuilder constructorDocumento = fabricaConstructorDocumento.newDocumentBuilder();
	           File fichero = new File ("./src/main/resources/paises.xml");
	           Document documento = constructorDocumento.parse(fichero);
	           
	           NodeList listaPaises, listaPais;
	           Element paises, pais, id, nombre;
	           int indicePaises, indicePais;
	           listaPaises = documento.getElementsByTagName("paises");
	           indicePaises=0;
	           
	           //leemos los paises
	           while(indicePaises<listaPaises.getLength()) {
	        	   
	        	   paises = (Element) listaPaises.item(indicePaises);
	        	   
	        	   listaPais = paises.getElementsByTagName("pais");
	        	   indicePais=0;
	        	  
	        	   while(indicePais<listaPais.getLength()) {
	        		   
	        		   pais =(Element) listaPais.item(indicePais);
	        		   //LEEMOS LOS ELEMENTOS DE PAIS
	        		   id = (Element) pais.getElementsByTagName("id").item(0);
	        		   nombre = (Element) pais.getElementsByTagName("nombre").item(0);
	        		   
	        		   mapaPaises.put(id.getTextContent(), nombre.getTextContent());
	        		   
	        		   indicePais++;
	        	   }
	        	   indicePaises++;
	           }
		}
		
		
		catch(IOException e){
			System.out.println("ERROR");
			e.printStackTrace();
		}
		catch(ParserConfigurationException e) {
			System.out.println("Error: "+e.getMessage());e.printStackTrace();
		} catch (SAXException e) {
			System.out.println("Error: "+e.getMessage());
			e.printStackTrace();
		}
		
	}

}
