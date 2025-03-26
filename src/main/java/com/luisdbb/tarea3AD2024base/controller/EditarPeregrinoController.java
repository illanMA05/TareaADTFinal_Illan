package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

@Controller
public class EditarPeregrinoController implements Initializable{
	
	@FXML
	private TextField txtNombre;
	
	@FXML
	private TextField txtEmail;
	
	@FXML
	private ChoiceBox<String> pais;

	@Lazy
	@Autowired
	private StageManager stageManager;
	
	@Autowired
	private PeregrinoService pereService;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Peregrino p = pereService.findByNombreUsu(Sesion.sesion.getUsuario());
		
		txtNombre.setText(p.getNombre());
		txtEmail.setText(p.getEmail());
		pais.setValue(p.getNacionalidad());
	}
	
	

	@FXML 
	private void clickBtnAceptar(ActionEvent event) throws IOException{
		
		Peregrino p = pereService.findByNombreUsu(Sesion.sesion.getUsuario());
		
		if(txtNombre.getText()!=p.getNombre() || txtEmail.getText()!=p.getEmail() || pais.getValue()!= p.getNacionalidad()) {
		
			boolean nomCorrecto=false;	
			boolean emailCorrecto=false;
			boolean paisCorrecto= false;
			
			String nom= txtNombre.getText();
			if(nom==null) {
				//poner algo para que envie mensaje al  usuario d q esta mal
			}
			else {
				boolean mal=false;
				for(int i=0; i<nom.length();i++ ) {
					
					if(Character.isDigit(nom.charAt(i))|| !Character.isAlphabetic(nom.charAt(i))) {
						//poner algo para que envie mensaje al  usuario d q esta mal
						mal=true;
					}					
				}	
				if(!mal) nomCorrecto=true;
				else {
					Alert mensaje = new Alert(Alert.AlertType.WARNING);
					mensaje.setTitle("NOMBRE INVALIDO");
					mensaje.setContentText("EL NOMBRE DEL PEREGRINO NO PUEDE CONTENER NINGUN CARACTER NO ALFABETICO O NUMEROS");
					mensaje.showAndWait();
				}
			}
			
			String email = txtEmail.getText();
			
			if(validarCorreo(email)==true) {
				emailCorrecto=true;
			}
			else emailCorrecto=false;
			
			
			if( pais.getValue() == null) {
				
			}else 
				paisCorrecto=true;
			
			if(nomCorrecto && emailCorrecto && paisCorrecto) {
				
				p.setNombre(nom);
				p.setEmail(email);
				p.setNacionalidad(pais.getValue());
				
				pereService.save(p);
				
				Alert mensaje = new Alert(Alert.AlertType.WARNING);
				mensaje.setTitle("EDITADO CORRECTAMENTE");
				mensaje.setContentText("PEREGRINO EDITADO CORRECTAMENTE, DIRIGIENDO AL MENU DEL PEREGRINO");
				mensaje.showAndWait();
				
				Sesion.sesion = new Sesion(p.getNombreUsuario());
				stageManager.switchScene(FxmlView.PEREGRINOIDENTIFICADO);
				
				
			}else {
				
				Alert mensaje = new Alert(Alert.AlertType.WARNING);
				mensaje.setTitle("CAMPOS MAL");
				mensaje.setContentText("CAMPOS MAL, RIVESELOS");
				mensaje.showAndWait();
				
			}
			
		}
		else {
			Alert mensaje = new Alert(Alert.AlertType.CONFIRMATION);
			mensaje.setTitle("NINGUN CAMBIO");
			mensaje.setContentText("NO HA REALIZADO NINGUN CAMBIO EN LOS CAMPOS, ES CORRECTO?");
			Optional <ButtonType> opcion = mensaje.showAndWait();
			if(opcion.isPresent() &&opcion.get().equals(ButtonType.OK)) {
				stageManager.switchScene(FxmlView.PEREGRINOIDENTIFICADO);
			}
		}
	}
	
	@FXML
	public void clickBtnVolver(ActionEvent event) throws IOException{
		stageManager.switchScene(FxmlView.PEREGRINOIDENTIFICADO);
	}
	
	
public static boolean validarCorreo(String correo) {
        
        String expresionRegular = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        
        Pattern pattern = Pattern.compile(expresionRegular);
        Matcher matcher = pattern.matcher(correo);
        
        return matcher.matches();
    }
}
