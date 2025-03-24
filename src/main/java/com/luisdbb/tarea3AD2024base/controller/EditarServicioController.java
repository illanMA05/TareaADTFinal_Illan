package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.services.Servicios_ConjContraService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

@Controller
public class EditarServicioController  implements Initializable{
	
	@Lazy
	@Autowired
	private StageManager stageManager;
	
	@FXML
	private Button btnVolver;
	
	@FXML
	private Button btnEditar;
	
	@FXML
	private TableView<Servicio> tablaServicios;
	
	@FXML
	private TableColumn <String, String> clmPrecio;
	
	@FXML
	private TableColumn <String, String>clmNombre;
	
	@Autowired
	private Servicios_ConjContraService serConjService;
	
	public ObservableList<Servicio> servicios;
	
	public static Servicio servicioSeleccionado;
	@FXML
	public void clickBtnVolver(ActionEvent event) throws IOException{
		stageManager.switchScene(FxmlView.ADMIN);
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		servicios = FXCollections.observableArrayList();
		
		this.clmNombre.setCellValueFactory(new PropertyValueFactory<String, String>("Nombre"));
		this.clmPrecio.setCellValueFactory(new PropertyValueFactory<String, String>("Precio"));	
		
		for(Servicio s : serConjService.findAllServicios()) {
			servicios.add(s);
		}
		tablaServicios.setItems(servicios);
	}
	
	@FXML
	public void clickBtnEditar(ActionEvent event) throws IOException{
		Servicio s = tablaServicios.getSelectionModel().getSelectedItem();
		if(s == null) {
			Alert mensaje = new Alert(Alert.AlertType.WARNING);
			mensaje.setTitle("SERVICIO INVALIDO");
			mensaje.setContentText("SELECCIONE UN SERVICIO DE LA TABLA ANTES DE EDITAR");
			mensaje.showAndWait();
		}
		else {
			
			servicioSeleccionado = s;
			
			stageManager.switchScene(FxmlView.EDITARSERVICIOSELECCIONADO);
		}
		
	}

}
