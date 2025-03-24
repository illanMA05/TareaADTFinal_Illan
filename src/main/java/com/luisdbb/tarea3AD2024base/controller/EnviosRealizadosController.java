package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Direccion;
import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;
import com.luisdbb.tarea3AD2024base.modelo.Paradas;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.EnvioACasaService;
import com.luisdbb.tarea3AD2024base.services.EstanciasService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.Servicios_ConjContraService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

@Controller
public class EnviosRealizadosController implements Initializable{
	
	@FXML
	private Button btnVolver;
	
	@FXML
	private Label lblNomParada;
	
	@FXML
	private Label lblRegion;
	
	@FXML
	private Label lblResponsable;
	
	
	@FXML
	private TableView tablaEnvios;
	
	@FXML
	private TableColumn clmId;
	
	@FXML
	private TableColumn clmPeso;
	
	@FXML
	private TableColumn <EnvioACasa, String>clmVolumen;
	
	@FXML
	private TableColumn clmUrgente;
	
	@FXML
	private TableColumn<EnvioACasa, String> clmDireccion;
	
	@FXML
	private TableColumn <EnvioACasa, String>clmLocalidad;
	
		
	@Autowired
	private ParadaService paradaService;
	
	@Lazy
	@Autowired
	private StageManager stageManager;
	
	@Autowired
	private EnvioACasaService envioService;
	
	@Autowired
	private Servicios_ConjContraService serConjService;
	
	@Autowired
	private EstanciasService estanciasService;
	
	public ObservableList <EnvioACasa> conjunto;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		lblResponsable.setText(Sesion.sesion.getUsuario());
		
		Paradas p =paradaService.findByResponsable(Sesion.sesion.getUsuario());
		
		lblNomParada.setText(p.getNombre());
		lblRegion.setText(p.getRegion()+"");
		
		conjunto = FXCollections.observableArrayList();
		
		this.clmId.setCellValueFactory(new PropertyValueFactory("id"));
		this.clmPeso.setCellValueFactory(new PropertyValueFactory("peso"));
		this.clmUrgente.setCellValueFactory(new PropertyValueFactory("urgente"));
		//this.clmVolumen.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().datosVolumen(cellData.getValue().getVolumen())));	
		
		clmVolumen.setCellValueFactory( cellData -> new SimpleStringProperty(Arrays.toString(cellData.getValue().getVolumen())));
		clmDireccion.setCellValueFactory( cellData -> new SimpleStringProperty(cellData.getValue().getDireccion().getDireccion()));
		clmLocalidad.setCellValueFactory( cellData -> new SimpleStringProperty(cellData.getValue().getDireccion().getLocalidad()));
		
		
		if(envioService.findAllEnvios().size()==0) {
			Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
			mensaje.setTitle("NO HAY ENVIOS");
			mensaje.setContentText("EN ESTE MOMENTO NO HAY ENVIOS REALIZADOS, VOLVIENDO AL MENU...");
			mensaje.showAndWait();
			
			stageManager.switchScene(FxmlView.ADMINPARADA);
			
		}else {
		
		for(EnvioACasa env: envioService.findAllEnvios()) {

			if(env.getIdParada() == p.getIdPa()) {
				conjunto.add(env);
			}	
		}	
		tablaEnvios.setItems(conjunto);
		}
		
	}
	
	
	@FXML
	public void clickBtnVolver(ActionEvent event) throws IOException{
		stageManager.switchScene(FxmlView.ADMINPARADA);
	}
	
	

}
