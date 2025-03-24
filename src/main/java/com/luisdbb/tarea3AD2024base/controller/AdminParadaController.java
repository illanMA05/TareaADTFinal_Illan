package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Paradas;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.PeregrinoParadas;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class AdminParadaController implements Initializable{

	
	@FXML
	private Button btnCerrarSesion;
	
	@FXML
	private Button btnSellarPeregrino;
	
	@FXML
	private Button btnAyuda;
	
	@FXML
	private Button btnCarnets;
	
	@FXML
	private Button btnFiltrar;
	
	@FXML
	private Button btnEnvios;
	
	@FXML
	private DatePicker fechaMenor;
	
	@FXML
	private DatePicker fechaMayor;
	
	@FXML
	private Label lblNomParada;
	
	@FXML
	private Label lblRegion;
	
	@FXML
	private Label lblResponsable;
	
	@FXML
	private TableView<Peregrino> tablaPeres;
	
	@FXML
	private TableColumn <String, String> clmNombre;
	
	@FXML
	private TableColumn <String, String>clmNomUsu;
	
	@FXML
	private TableColumn<String, String>clmNacionalidad;
	
	@FXML
	private TableColumn <String, String>clmEmail;
	
	@Autowired
	private ParadaService paradaService;
	
	@Autowired
	private PeregrinoService pereService;
	
	@Autowired
	private PeregrinoParadaService ppService;
	
	@Lazy
	@Autowired
	private StageManager stageManager;
	
	public ObservableList <Peregrino> peres;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		peres = FXCollections.observableArrayList();
		
		this.clmNombre.setCellValueFactory(new PropertyValueFactory<String, String>("Nombre"));
		this.clmNomUsu.setCellValueFactory(new PropertyValueFactory<String, String>("NombreUsuario"));
		this.clmNacionalidad.setCellValueFactory(new PropertyValueFactory<String, String>("Nacionalidad"));
		this.clmEmail.setCellValueFactory(new PropertyValueFactory<String, String>("Email"));
		
		lblResponsable.setText(Sesion.sesion.getUsuario());
		
		Paradas p =paradaService.findByResponsable(Sesion.sesion.getUsuario());
		
		lblNomParada.setText(p.getNombre());
		lblRegion.setText(p.getRegion()+"");
		
		for(PeregrinoParadas pere : ppService.findByParadasEquals(p)) {
			Peregrino pe = pereService.findById(pere.getPeregrino().getIdP());
			
			peres.add(pe);
		}
		tablaPeres.setItems(peres);
		
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
	public void clickBtnFiltrar(ActionEvent event)throws IOException{
		Paradas p =paradaService.findByResponsable(Sesion.sesion.getUsuario());
		
		if(fechaMenor.getValue()==null || fechaMayor.getValue()==null) {
			Alert mensaje = new Alert(Alert.AlertType.WARNING);
			mensaje.setTitle("FECHAS INCORRECTAS");
			mensaje.setContentText("LAS FECHAS NO PUEDEN ESTAR VACIAS PARA REALIZAR EL FILTRADO");
			mensaje.showAndWait();
	
		}else {
			if(!fechaMenor.getValue().isAfter(fechaMayor.getValue())) {
				//meter en la tabla los campos con la validacion
				peres.clear();
				
				for(PeregrinoParadas pere : ppService.findByFechaIsBetweenAndParadasEquals(fechaMenor.getValue(), fechaMayor.getValue(),p)) {
					Peregrino pe = pereService.findById(pere.getPeregrino().getIdP());
					
					peres.add(pe);
				}
				tablaPeres.setItems(peres);
				
				
			}else {
				Alert mensaje = new Alert(Alert.AlertType.WARNING);
				mensaje.setTitle("FECHAS INCORRECTAS");
				mensaje.setContentText("LA FECHA DE INICIO NO PUEDE SER POSTERIOR A LA FECHA DE FIN ");
				mensaje.showAndWait();
			}
			
		}
		
	}
	
	@FXML
	public void clickBtnCerrarSesion(ActionEvent event) throws IOException{
		
		Alert mensaje = new Alert(Alert.AlertType.CONFIRMATION);
		mensaje.setTitle("	CERRAR SESION	");
		mensaje.setContentText("VA A CERRAR SESION EN SU CUENTA."
				+ "¿ESTA SEGURO DE ESTA ACCION?");
		Optional <ButtonType> opcion = mensaje.showAndWait();
		if(opcion.isPresent() &&opcion.get().equals(ButtonType.OK)) {
		stageManager.switchScene(FxmlView.LOGIN);
		}
	}
	
	@FXML
	public void clickBtnSellar(ActionEvent event) throws IOException{
		stageManager.switchScene(FxmlView.SELLAR);
	}
	
	@FXML
	public void clickBtnVerEnvios(ActionEvent event) throws IOException{
		stageManager.switchScene(FxmlView.VERENVIOS);
	}
	
	@FXML
	public void clickBtnCarnets(ActionEvent event) throws IOException{
		stageManager.switchScene(FxmlView.VERCARNETS);
	}

	
	
	

}
