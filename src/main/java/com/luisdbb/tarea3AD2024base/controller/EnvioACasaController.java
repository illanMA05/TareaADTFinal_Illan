package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.ConexionObjectDB;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Direccion;
import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;
import com.luisdbb.tarea3AD2024base.modelo.Paradas;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.EnvioACasaService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import jakarta.persistence.EntityManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

@Controller
public class EnvioACasaController {
	
	@FXML
	private TextField txtPeso;
	
	@FXML
	private TextField txtLargo;
	
	@FXML
	private TextField txtAncho;
	
	@FXML
	private TextField txtProfundidad;
	
	@FXML
	private TextField txtDireccion;
	
	@FXML
	private TextField txtLocalidad;
	
	@FXML
	private CheckBox checkUrgente;
	
	@Autowired
	private EnvioACasaService envioService;
	
	@Autowired
	private ParadaService paradaService;
	
	@Lazy
	@Autowired
	private StageManager stageManager;

	
	EntityManager odb = ConexionObjectDB.em;
	
	
	public void clickBtnAceptar(ActionEvent event) throws IOException
	{
		
		boolean direccionBien = false;
		boolean localBien = false;
		
		if(!txtDireccion.getText().isEmpty() || !txtDireccion.getText().isBlank()) direccionBien=true;
		
		boolean mal = false;
		for(int i=0; i>=txtLocalidad.getText().length(); i++) {
			
			if(!Character.isAlphabetic(txtLocalidad.getText().charAt(i))) {
				mal = true;
			}
			
		}
		if(!mal) localBien = true;
		
		
		if( localBien && direccionBien&& ValidarPeso(txtPeso.getText()) && ValidarVolumenes(txtLargo.getText()) && ValidarVolumenes(txtAncho.getText()) && ValidarVolumenes(txtProfundidad.getText()))
		{
			
			 double peso = Double.parseDouble(txtPeso.getText());
			 int largo = Integer.parseInt(txtLargo.getText());
			 int ancho = Integer.parseInt(txtAncho.getText());
			 int profundidad = Integer.parseInt(txtProfundidad.getText());
			 
			 int[] volumen = {largo, ancho, profundidad};
			 
			 Paradas p =paradaService.findByResponsable(Sesion.sesion.getUsuario());
			 
		 
				Long id=0L;
        		List<EnvioACasa> ps2 = envioService.findAllEnvios();
        			for (EnvioACasa ter : ps2) {
        				if(ter==null) {
        					
	        				}else {
	        					id=ter.getId();
	        				}
               		 
                        }	       		       		 
        		 	id++;
        		 	
        
			 		 
			 Direccion d = new Direccion(id,txtDireccion.getText(),txtLocalidad.getText());
			 EnvioACasa en = new EnvioACasa(0L,"Envio a casa",4.99,null,null,id,peso, volumen,checkUrgente.isSelected(),p.getIdPa(),d);
		
				odb.getTransaction().begin();
				odb.persist(en);
				odb.getTransaction().commit();
			
			Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
			mensaje.setTitle("PESIDO REALIZADO CORRECTAMENTE");
			mensaje.setContentText("EL ENVIO A CASA SE REALIZO CORRECTAMENTE, VOLVIENDO AL MENU...");
			mensaje.showAndWait();

			
			stageManager.switchScene(FxmlView.ADMINPARADA);
				
		}
		else {
			
			Alert mensaje = new Alert(Alert.AlertType.WARNING);
			mensaje.setTitle("CAMPOS INCORRECTOS");
			mensaje.setContentText("EL PESO DEBE TENER 2 DECIMALES Y NO MAS DE CENTENAS, LAS MEDIDAS SON EN CM Y MAXIMO 999, NO PUEDEN LLEVAR DECIMALES, LA LOCALIDAD SOLO ADMITE LETRAS");
			mensaje.showAndWait();
			
		}	
		
	}
		
	private boolean ValidarPeso(String precio) {
        String exp = "^([0-9]{0,3}).([0-9]{2}$)";
        Pattern patron = Pattern.compile(exp);
        Matcher coincide = patron.matcher(precio);
        return coincide.matches();
    }
	
	private boolean ValidarVolumenes(String volumen) {
		
		String exp ="^([0-9]{1,3}$)";
		 Pattern patron = Pattern.compile(exp);
	     Matcher coincide = patron.matcher(volumen);
	     return coincide.matches();
	}
}
