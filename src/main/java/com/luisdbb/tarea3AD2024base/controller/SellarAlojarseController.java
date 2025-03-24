package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Credenciales;
import com.luisdbb.tarea3AD2024base.modelo.Estancias;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.PeregrinoParadas;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.CarnetService;
import com.luisdbb.tarea3AD2024base.services.CredencialesService;
import com.luisdbb.tarea3AD2024base.services.EstanciasService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class SellarAlojarseController implements Initializable{

	@FXML
	private Button btnVolver;
	
	@FXML
	private Button btnAyuda;
	
	@FXML
	private Button btnAceptar;
	
	@FXML
	private ChoiceBox<String> cbPeregrino;
	
	@FXML 
	private Label lblVip;
	
	@FXML 
	private Label lblServicios;
	
	@FXML
	private CheckBox checkEstancia;
	
	@FXML
	private CheckBox checkVip;
	
	@FXML
	private CheckBox checkServicios;
	
	@Autowired
	private EstanciasService estanciaService;
	
	@Autowired
	private CarnetService carnetService;
	
	@Autowired
	private PeregrinoService pereService;
	
	@Autowired
	private CredencialesService credenService;
	
	@Autowired
	private PeregrinoParadaService ppService;
	
	@Lazy
	@Autowired
	private StageManager stageManager;
	
	public List<Peregrino> peres;
	
	public static Estancias estancia;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		peres = pereService.findAll();
		
		for(Peregrino p: peres) {
			cbPeregrino.getItems().add(p.getNombre());
		}
		
		
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
	public void clickBtnVolver(ActionEvent event) throws IOException{
		stageManager.switchScene(FxmlView.ADMINPARADA);
	}
	
	

	@FXML
	public void activarVip(ActionEvent evetn) throws IOException{
		if(checkEstancia.isSelected()) {
			lblVip.setDisable(false);
			checkVip.setDisable(false);
			lblServicios.setDisable(false);
			checkServicios.setDisable(false);
			
		}else {
			checkVip.setSelected(false);
			lblVip.setDisable(true);
			checkVip.setDisable(true);
			lblServicios.setDisable(true);
			checkServicios.setSelected(false);
			checkServicios.setDisable(true);
		}
	}
	
	@FXML
	public void clickBtnAceptar(ActionEvent event) throws IOException{
		
		Credenciales c = credenService.findByNombre(Sesion.sesion.getUsuario());
		
		
		
		boolean pereCorrecto=false;
		boolean noEstanciar=false;
		
		//validacion del peregrino
		if(cbPeregrino.getValue()!=null) pereCorrecto=true;
		
		if(noEstanciar) {
			Alert mensaje = new Alert(Alert.AlertType.WARNING);
			mensaje.setTitle("NO ES POSIBLE REALIZAR SELLADO");
			mensaje.setContentText("EL PEREGRINO YA SELLÓ HOY EN ESTA PARADA, NO ES POSIBLE SELLAR DE NUEVO. \nVOLVIENDO AL MENU DE ADMIN");
			mensaje.showAndWait();
			
			stageManager.switchScene(FxmlView.ADMINPARADA);
		}
		else {
	
		
		if(pereCorrecto) {
			
			 
			
			if(checkEstancia.isSelected()) {
				
				//SITUACION EN LA QUE SELLA Y ALOJA CON VIP
				if(checkVip.isSelected()) {
					Peregrino p = pereService.findByNombre(cbPeregrino.getValue());
					PeregrinoParadas pp = new PeregrinoParadas(LocalDate.now());
					Carnet carnet = p.getCarnet();
					Estancias es = new Estancias(LocalDate.now(),true);
					
					es.setParadaE(c.getParada());
					es.setPeregrinoE(p);
					estanciaService.save(es);
					
					pp.setPeregrino(p);
					pp.setParadas(c.getParada());			
					ppService.save(pp);
					
					carnet.setDistancia(carnet.getDistancia()+5);
					carnet.setNvips(carnet.getNvips()+1);
					carnetService.actualizarEntidad(carnet);
					
					Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
					mensaje.setTitle("PEREGRINO SELLADO");
					mensaje.setContentText("EL PEREGRINO SE HA SELLADO  Y ALOJADO VIP CORRECTAMENTE, VOLVIENDO AL MENU...");
					mensaje.showAndWait();
					
					
					estancia = es;
				}
				//SITUACION EN LA QUE SE SELLA Y ALOJA SIN VIP
				else {
					Peregrino p = pereService.findByNombre(cbPeregrino.getValue());
					PeregrinoParadas pp = new PeregrinoParadas(LocalDate.now());
					Carnet carnet = p.getCarnet();
					Estancias es = new Estancias(LocalDate.now(),false);
					
					es.setParadaE(c.getParada());
					es.setPeregrinoE(p);
					estanciaService.save(es);

					
					pp.setPeregrino(p);
					pp.setParadas(c.getParada());			
					ppService.save(pp);
					
					carnet.setDistancia(carnet.getDistancia()+5);
					carnetService.actualizarEntidad(carnet);
					
					Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
					mensaje.setTitle("PEREGRINO SELLADO");
					mensaje.setContentText("EL PEREGRINO SE HA SELLADO Y ALOJADON SIN VIP CORRECTAMENTE");
					mensaje.showAndWait();
					
					
					estancia = es;
				}
				
				
				if(checkServicios.isSelected()) {
					stageManager.switchScene(FxmlView.SELECCIONARSERVICIOS);
					
				}
				else {
					stageManager.switchScene(FxmlView.ADMINPARADA);
				}
				
			}
			//SITUACION EN LA QUE SOLO SE SELLA AL PERE
			else {
				Peregrino p = pereService.findByNombre(cbPeregrino.getValue());
				PeregrinoParadas pp = new PeregrinoParadas(LocalDate.now());
				Carnet carnet = p.getCarnet();
				
				pp.setPeregrino(p);
				pp.setParadas(c.getParada());			
				ppService.save(pp);
				
				
				
				carnet.setDistancia(carnet.getDistancia()+5);
				carnetService.actualizarEntidad(carnet);
				
				Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
				mensaje.setTitle("PEREGRINO SELLADO");
				mensaje.setContentText("EL PEREGRINO SE HA SELLADO CORRECTAMENTE");
				mensaje.showAndWait();
				
				stageManager.switchScene(FxmlView.ADMINPARADA);
				
			}
			
		}
	
		else {
			
			Alert mensaje = new Alert(Alert.AlertType.WARNING);
			mensaje.setTitle("PEREGRINO NO SELECCIONADO");
			mensaje.setContentText("SELECCIONE UN PEREGRINO PARA SELLAR Y/O ALOJARSE");
			mensaje.showAndWait();
			
		}
		}
		
		
	}



	

}
