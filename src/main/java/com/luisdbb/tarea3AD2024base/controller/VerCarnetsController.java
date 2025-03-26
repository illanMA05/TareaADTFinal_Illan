package com.luisdbb.tarea3AD2024base.controller;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;

import com.luisdbb.tarea3AD2024base.config.ConexionExistDB;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Paradas;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

@Controller
public class VerCarnetsController implements Initializable{
	
	@FXML
	private Button btnVolver;
	
	@FXML
	private Label lblNomParada;
	
	@FXML
	private Label lblRegion;
	
	@FXML
	private Label lblResponsable;
	
	@FXML
	private TableView<Peregrino> tablaCarnets;
	
	@FXML
	private TableColumn <Peregrino, String> clmNombre;
	
	@Autowired
	private ParadaService paradaService;
	
	@Autowired
	private PeregrinoService pereService;
	
	
	@Lazy
	@Autowired
	private StageManager stageManager;
	
	public ObservableList <Peregrino> nombres;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblResponsable.setText(Sesion.sesion.getUsuario());
		nombres = FXCollections.observableArrayList();
		
		Paradas p =paradaService.findByResponsable(Sesion.sesion.getUsuario());
		
		lblNomParada.setText(p.getNombre());
		lblRegion.setText(p.getRegion()+"");
		
		this.clmNombre.setCellValueFactory(new PropertyValueFactory<Peregrino, String>("nombre"));
		
		 //listar los elementos de la coleccion
		ConexionExistDB cEDB = new ConexionExistDB();
		Collection col = cEDB.ConexionExistAParada(p.getNombre());
		
		try {
			String carnets[] = col.listResources();
			for (String s: carnets) {
				String[] nom = s.split("_");
				Peregrino pe = pereService.findByNombreUsu(nom[0]);
				nombres.add(pe);
			}
			tablaCarnets.setItems(nombres);
		}
		catch (XMLDBException e) {
			e.printStackTrace();
		}
		
		
	}
	
	@FXML
	public void clickBtnVolver(ActionEvent event) throws IOException{
		stageManager.switchScene(FxmlView.ADMINPARADA);
	}
	
	
}
