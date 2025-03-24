package com.luisdbb.tarea3AD2024base.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ConexionObjectDB {
	
	  public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("objectdb://localhost:6136/peregrinosIllanMA.odb;user=admin;password=admin");
	 
	       public static EntityManager em = emf.createEntityManager();

		public static EntityManagerFactory getEmf() {
			return emf;
		}

		public static void setEmf(EntityManagerFactory emf) {
			ConexionObjectDB.emf = emf;
		}

		public static EntityManager getEm() {
			return em;
		}

		public static void setEm(EntityManager em) {
			ConexionObjectDB.em = em;
		}
	       

}
