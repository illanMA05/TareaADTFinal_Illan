package com.luisdbb.tarea3AD2024base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

///@Configuration
public class ConexionMongoDB {
	
	@Value("${spring.data.mongodb.uri}")
	private String uri;

	@Value("${spring.data.mongodb.database}")
	private String bd;

//	@Bean
//	public MongoClient mongoClient() {
//		return MongoClients.create(uri);
//	}
//
//	@Bean
//	public MongoDatabase mongoGetDatabase(MongoClient mongoClient) {
//		return mongoClient.getDatabase(bd);
//	}
	
	  private MongoClient mongoClient; 

	    public MongoDatabase conectar() {
	        try {
	            if (mongoClient == null) { 
	                mongoClient = MongoClients.create(uri);
	            }
	            return mongoClient.getDatabase(bd);
	        } catch (Exception e) {
	            System.out.println("Error al conectar con MongoDB: " + e.getMessage());
	            return null;
	        }
	    }
	   
	    public void cerrarConexion() {
	        if (mongoClient != null) {
	            mongoClient.close();
	            mongoClient = null;
	        }
	    }
	
}