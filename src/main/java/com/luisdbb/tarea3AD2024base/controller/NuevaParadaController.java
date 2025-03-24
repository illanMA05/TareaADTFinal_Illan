package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;

import com.luisdbb.tarea3AD2024base.config.ConexionExistDB;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Credenciales;
import com.luisdbb.tarea3AD2024base.modelo.Paradas;
import com.luisdbb.tarea3AD2024base.services.CredencialesService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class NuevaParadaController {
	
	@FXML
	private Button btnVolver;
	
	@FXML
	private Button btnAceptar;
	
	@FXML
	private Button btnAyuda;
	
	@FXML
	private TextField txtNombreParada;
	
	@FXML
	private TextField txtRegion;
	
	@FXML
	private TextField txtNombreResponsable;
	
	@FXML
	private TextField txtContrasenia;
	
	@Autowired
	private CredencialesService credenService;
	
	@Autowired
	private ParadaService paradaService;
	
	@Lazy
	@Autowired
	private StageManager stageManager;
	
	@FXML
	public void clickBtnVolver(ActionEvent event) throws IOException {
		
		stageManager.switchScene(FxmlView.ADMIN);
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
	public void clickBtnAceptar(ActionEvent event) throws IOException{
		boolean nomParadaCorrecto=false;
		boolean regionCorrecto = false;
		boolean nomResponCorrecto = false;
		boolean contraCorrecto = false;
		
		String nom= txtNombreParada.getText();
		
		//validacion del nombre
				if(nom==null || nom.length()==0) {
					//poner algo para que envie mensaje al  usuario d q esta mal
					
					Alert mensaje = new Alert(Alert.AlertType.WARNING);
					mensaje.setTitle("NOMBRE DE PARADA INVALIDO");
					mensaje.setContentText("EL NOMBRE DE LA PARADA NO ES VALIDO");
					mensaje.showAndWait();
				}
				else {
					boolean mal=false;
					for(int i=0; i<nom.length();i++ ) {
						
						if(Character.isDigit(nom.charAt(i))|| !Character.isAlphabetic(nom.charAt(i))) {
							//poner algo para que envie mensaje al  usuario d q esta mal
							mal=true;
						}					
					}	
					if(!mal) nomParadaCorrecto=true;
				}
				
			//validacion de la region de la parada
				String region = txtRegion.getText();
				if(region.equals(null) || region.length()>=2 || region.length()==0 ||!Character.isAlphabetic(region.charAt(0))) {
					Alert mensaje = new Alert(Alert.AlertType.WARNING);
					mensaje.setTitle("REGION NO VALIDA");
					mensaje.setContentText("LA REGION NO ES CORRECTA");
					mensaje.showAndWait();
				}else regionCorrecto=true;
				
			//validacion del nombre del responsable
				String nomUsu = txtNombreResponsable.getText();
				if(nomUsu==null || nomUsu.length()==0) {
				 
					Alert mensaje = new Alert(Alert.AlertType.WARNING);
					mensaje.setTitle("NOMBRE DE RESPONSABLE NO VALIDO");
					mensaje.setContentText("EL NOMBRE DEL RESPONSABLE NO ES VALIDO");
					mensaje.showAndWait();
					}
				{
					boolean mal= false;
					if(nomUsu.equals("admin")) {
						mal=true;
					}
					else {
					for(int i=0; i<nomUsu.length();i++ ) {
						if(Character.isWhitespace(nomUsu.charAt(i))) {
							mal = true;
						}
					}
					}
					if(!mal) nomResponCorrecto=true;
					else {
						Alert mensaje = new Alert(Alert.AlertType.WARNING);
						mensaje.setTitle("NOMBRE DE USUARIO INVALIDO");
						mensaje.setContentText("EL NOMBRE DE USUARIO NO PUEDE CONTENER ESPACIOS");
						mensaje.showAndWait();
					}
				}
				
		//validacion de la contraseña del responsable
				String contra = txtContrasenia.getText();
				if(contra==null || contra.length()==0) {
					Alert mensaje = new Alert(Alert.AlertType.WARNING);
					mensaje.setTitle("CONTRASEÑA NO VALIDA");
					mensaje.setContentText("LA CONTRASEÑA NO ES VALIDA");
					mensaje.showAndWait();
				}
				else {
					boolean mal=false;
					for(int i=0; i<contra.length();i++ ) {
						if(Character.isWhitespace(contra.charAt(i))) {
							mal = true;
						}
										
					}
					if(!mal) contraCorrecto = true;
				}
				
				if(contraCorrecto && nomResponCorrecto && nomParadaCorrecto && regionCorrecto) {
					
					if(!credenService.CredencialesExistenPorNombre(txtNombreResponsable.getText())) {
						if(!paradaService.ParadaExistePorNombre(txtNombreParada.getText())) {
							char c = txtRegion.getText().charAt(0);
							Paradas p = new Paradas(txtNombreParada.getText(), c , txtNombreResponsable.getText());
							Credenciales creden = new Credenciales (txtNombreResponsable.getText(), txtContrasenia.getText(), "parada");
							
							
							
							p.setCredenciales(creden);
							creden.setParada(p);
							
							paradaService.save(p);
							
							ConexionExistDB cEdb = new ConexionExistDB();
							
							Collection col = cEdb.ConexionExist();
							
							
							try {
								CollectionManagementService mgtService = (CollectionManagementService) col.getService("CollectionManagementService", "1.0");
								mgtService.createCollection(txtNombreParada.getText());
							} catch (XMLDBException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
							Alert mensaje = new Alert(Alert.AlertType.WARNING);
							mensaje.setTitle("PARADA CREADA CORRECTAMENTE");
							mensaje.setContentText("PARADA CREADA CORRECTAMENTE, VOLVIENDO AL MENU...");
							mensaje.showAndWait();
							
							stageManager.switchScene(FxmlView.ADMIN);
							
							
						}else {
							Alert mensaje = new Alert(Alert.AlertType.WARNING);
							mensaje.setTitle("YA ESXISTE UNA PARADA CON ESTE NOMBRE");
							mensaje.setContentText("CAMBIE EL NOMBRE DE LA PARADA PARA PODER COMPLETAR EL REGISTRO");
							mensaje.showAndWait();
						}	
						
					}
					else {
						Alert mensaje = new Alert(Alert.AlertType.WARNING);
						mensaje.setTitle("YA ESXISTE UN USUARIO CON ESTE NOMBRE DE USUARIO");
						mensaje.setContentText("CAMBIE EL NOMBRE DE USUARIO PARA PODER COMPLETAR EL REGISTRO");
						mensaje.showAndWait();
					}
					
				}else {
					Alert mensaje = new Alert(Alert.AlertType.WARNING);
					mensaje.setTitle("CAMPOS MAL");
					mensaje.setContentText("CAMPOS MAL, RIVESELOS");
					mensaje.showAndWait();
				}
	}

}
