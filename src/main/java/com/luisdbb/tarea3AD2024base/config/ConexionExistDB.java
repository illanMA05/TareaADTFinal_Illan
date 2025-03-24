package com.luisdbb.tarea3AD2024base.config;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

public class ConexionExistDB {

	public Collection ConexionExist() {
		String URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db/paradas";
		
		Collection col = null;
		XMLResource res = null;
		try {
			///inicializar driver
			Class<?> cl = Class.forName("org.exist.xmldb.DatabaseImpl");
///			Database database = (Database) cl.newInstance();
///			database.setProperty("create-database", "true");
			Database database = (Database) cl.getDeclaredConstructor().newInstance();
			
			DatabaseManager.registerDatabase(database);
			/// conectar con una coleccion de existDB
			col = DatabaseManager.getCollection(URI, "admin", "");
			
			
//			/// Crear nueva collecion dentro de la coleccion en la que estemos
//			CollectionManagementService mgtService = (CollectionManagementService) col.getService("CollectionManagementService", "1.0");
//			mgtService.createCollection("paradas");
//			
//			System.out.println();
//			System.out.println("COLECCION PARADA CREADA CORRECTAMENTE");
//			System.out.println();
			return col;
			
		}catch (XMLDBException e1) {
			System.out.println("Error al conectar con ExistDB(XMLDBException)"+ e1.getLocalizedMessage());
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		catch (Exception e1) {
			System.out.println("Error al conectar con ExistDB"+ e1.getLocalizedMessage());
			e1.printStackTrace();
		}
		return col;

	}
	
	public Collection ConexionExistAParada(String parada) {
		String URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db/paradas/"+parada;
		
		Collection col = null;
		XMLResource res = null;
		try {
			///inicializar driver
			Class<?> cl = Class.forName("org.exist.xmldb.DatabaseImpl");
///			Database database = (Database) cl.newInstance();
///			database.setProperty("create-database", "true");
			Database database = (Database) cl.getDeclaredConstructor().newInstance();
			
			DatabaseManager.registerDatabase(database);
			/// conectar con una coleccion de existDB
			col = DatabaseManager.getCollection(URI, "admin", "");
			
			
//			/// Crear nueva collecion dentro de la coleccion en la que estemos
//			CollectionManagementService mgtService = (CollectionManagementService) col.getService("CollectionManagementService", "1.0");
//			mgtService.createCollection("paradas");
//			
//			System.out.println();
//			System.out.println("COLECCION PARADA CREADA CORRECTAMENTE");
//			System.out.println();
			return col;
			
		}catch (XMLDBException e1) {
			System.out.println("Error al conectar con ExistDB(XMLDBException)"+ e1.getLocalizedMessage());
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		catch (Exception e1) {
			System.out.println("Error al conectar con ExistDB"+ e1.getLocalizedMessage());
			e1.printStackTrace();
		}
		return col;

	}
	
	
			
}
