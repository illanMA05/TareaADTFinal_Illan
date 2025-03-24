package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.ConexionExistDB;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.CredencialesService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class InicioController implements Initializable{

	@FXML
	private TextField usuario;
	
	@FXML
	private TextField contrasenia;
	
	@FXML
	private Button btnAyuda;
	
	@FXML
	private PasswordField pswContrasenia;
	
	@FXML
	private Button btnAcceder;
	
	@FXML
	private Button btnRegistrarse;
	
	@FXML
	private Button btnContraVisible;
	
	@Autowired
	private CredencialesService credenService;
	
	@Autowired
	private ParadaService paradaService;
	
	
	
	@Lazy
	@Autowired
	private StageManager stageManager;
	
	@Value("${admin.name}")
	private String adminNombre;
	
	@Value("${admin.password}")
	private String adminContra;
	

	@FXML
	private void InicioSesion() {
		
	}
	
	@FXML
	public void clickBtnRegistro(ActionEvent event) throws IOException{
		if(paradaService.findAll().size()==0) {
			
			Alert mensa = new Alert(Alert.AlertType.WARNING);
			mensa.setTitle("IMPOSIBLE REGISTRAR");
			mensa.setContentText("EN ESTE MOMENTO NO ES POSIBLE REGISTRAR UN PEREGRINO YA QUE NO HAY PARADAS DISPONIBLES");
			mensa.showAndWait();
			
		}
		
		else
		stageManager.switchScene(FxmlView.NUEVOPERE);
	}
	
	
	@FXML
	public void clickBtnAyuda(ActionEvent event) throws IOException{
		WebView webView = new WebView();
		
		String url = getClass().getResource("/ayuda/help.html").toExternalForm();
		webView.getEngine().load(url);
		
		Stage helpStage = new Stage();
		
		Scene helpScene = new Scene ( webView, 663,408);
		
		helpStage.setScene(helpScene);
		helpStage.initModality(Modality.APPLICATION_MODAL);
		helpStage.setResizable(false);
		helpStage.centerOnScreen();
		
		
		
		helpStage.show();
	}
	
	@FXML
	public void hacerVisibleContrasenia(ActionEvent event) throws IOException{
		if(!contrasenia.isVisible()) {
			
			contrasenia.setVisible(true);
			contrasenia.setDisable(false);
			contrasenia.setText(pswContrasenia.getText());
			pswContrasenia.setVisible(false);
			pswContrasenia.setDisable(true);
			
		}else {
			
			contrasenia.setVisible(false);
			pswContrasenia.setText(contrasenia.getText());
			contrasenia.setDisable(true);
			pswContrasenia.setVisible(true);
			pswContrasenia.setDisable(false);
			
		}
		
		
	}


	@Override
	public void initialize(java.net.URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		ConexionExistDB cEdb = new ConexionExistDB();
		cEdb.ConexionExist();
		
	}

	@FXML
	public void clickBtnAcceder(ActionEvent event) throws IOException{
		if(contrasenia.isVisible()) {
			
		}else {
			contrasenia.setText(pswContrasenia.getText());
		}
		
		
		
		if(usuario.getText().equals(null) || contrasenia.getText().equals(null)) {
			
		}else {
			
			
		
		
			 if(credenService.CredencialesExisten(usuario.getText(), contrasenia.getText())) {
			
			Sesion.sesion = new Sesion(usuario.getText());
			
			if(credenService.perfilCredenciales(usuario.getText())==0){
				stageManager.switchScene(FxmlView.PEREGRINOIDENTIFICADO);
			}
				else if(credenService.perfilCredenciales(usuario.getText())==1) {
					stageManager.switchScene(FxmlView.ADMINPARADA);
				}
				 	
			
		}else if(usuario.getText().equals(adminNombre) && contrasenia.getText().equals(adminContra)) {
			stageManager.switchScene(FxmlView.ADMIN);	
		}
		
		else {
			Alert mensa = new Alert(Alert.AlertType.WARNING);
			mensa.setTitle("USUARIO O CONTRASEÑA INCORRECTOS");
			mensa.setContentText("ESTE USUARIO O CONTRASEÑA NO EXISTE");
			mensa.showAndWait();
		}
	}
	}
	
	
	
}
