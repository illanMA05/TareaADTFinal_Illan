package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.db4o.ObjectContainer;
import com.db4o.ext.Db4oException;
import com.luisdbb.tarea3AD2024base.config.DataConnection;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Paradas;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.Servicios_ConjContraService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


@Controller
public class NuevoServicioController implements Initializable{
	
	private ObjectContainer db = DataConnection.getInstance();

	@FXML
	private TextField txtNombre;
	
	@FXML
	private TextField txtPrecio;
	
	@FXML
	private Button btnVolver;
	
	@FXML 
	private Button btnAceptar;
	
	@FXML
	private TableView<Paradas> tablaParadas;
	
	@FXML
	private TableColumn clmId;
	
	@FXML
	private TableColumn <String, String>clmNombre;
	
	@FXML
	private TableColumn<String, String>clmRegion;
	
	@Autowired
	private Servicios_ConjContraService serConjService;
	
	
	@Lazy
	@Autowired
	private StageManager stageManager;
	
	@Autowired
	private ParadaService paradaService;

	public ObservableList <Paradas> paras;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		paras = FXCollections.observableArrayList();
		tablaParadas.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		this.clmNombre.setCellValueFactory(new PropertyValueFactory<String, String>("Nombre"));
		this.clmId.setCellValueFactory(new PropertyValueFactory<>("IdPa"));
		this.clmRegion.setCellValueFactory(new PropertyValueFactory<String, String>("Region"));
		
		for(Paradas p : paradaService.findAll()) {
			paras.add(p);
		}
		tablaParadas.setItems(paras);
		
		
	}
	
	@FXML
	public void clickBtnVolver(ActionEvent event) throws IOException {
		
		stageManager.switchScene(FxmlView.ADMIN);
	}
	
	
	
	@FXML
	public void clickBtnAceptar(ActionEvent event) throws IOException{
		boolean nombreCorrecto = false;
		boolean precioCorrecto = false;
		
		String nombre = txtNombre.getText();
		boolean mal = false;
		
		if(nombre.length()>0 || nombre == null) {		
			for (int i = 0; i>= nombre.length(); i++ ) {
				if(Character.isDigit(nombre.charAt(i))) {
					mal = true;
				}
			}	
		}
		if(!mal) nombreCorrecto = true;
		
		
		String precio = txtPrecio.getText();
		if(ValidarPrecio(precio))precioCorrecto=true;
		
		
		
		if(precioCorrecto && nombreCorrecto) {
			
			if(tablaParadas.getSelectionModel().getSelectedItems().size() == 0) {
				
				Alert mensa = new Alert(Alert.AlertType.WARNING);
				mensa.setTitle("SELECCIONE PARADAS");
				mensa.setContentText("TIENE QUE SELECCIONAR AL MENOS 1 PARADA EN LA QUE ESTÉ DISPONIBLE ESTE SERVICIO");
				mensa.showAndWait();
			}
			else {
				List<Long> idParadas = new ArrayList<Long>();
				
				for(Paradas p : tablaParadas.getSelectionModel().getSelectedItems()) {
					idParadas.add(p.getIdPa());
				}
				//añade el envio a casa a todas las paradas
				idParadas.add(0L);
				
				Long id=0L;
        		List<Servicio> ps2 = serConjService.findAllServicios();
        		 for (Servicio ter : ps2) {
        		 id=ter.getId();
                 }
        		 	id++;
				
				try {
					
					if(serConjService.findServiceByNombre(nombre) == false) {
						
						Servicio s1 = new Servicio(id, nombre, Double.parseDouble(precio),idParadas);
						
						db.store(s1);
						db.commit();
						
						Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
						mensaje.setTitle("SERVICIO CREADO CORRECTAMENTE");
						mensaje.setContentText("SERVICIO CREADO CORRECTAMENTE, VOLVIENDO AL MENU...");
						mensaje.showAndWait();
						
						stageManager.switchScene(FxmlView.ADMIN);
					}else {
						
						Alert mensaje = new Alert(Alert.AlertType.WARNING);
						mensaje.setTitle("SERVICIO YA EXISTENTE");
						mensaje.setContentText("YA EXISTE UN SERVICIO CON ESTE NOMBRE, CAMBIELO PARA CONTINUAR");
						mensaje.showAndWait();
						
					}
						
					
					
				}catch (Db4oException ee) {
					 JOptionPane.showMessageDialog(null,ee.getMessage());
					 db.rollback();
					 }
				
				
				
			}
			
			
		}
		else {
			Alert mensa = new Alert(Alert.AlertType.WARNING);
			mensa.setTitle("CAMPOS INCORRECTOR");
			mensa.setContentText("EL PRECIO DEBE LLEVAR 2 DECIMALES Y MAXIMO HASTA LAS CENTENAS "
					+ "Y EL NOMBRE SOLO PUEDE CONTENER LETRAS");
			mensa.showAndWait();
		}
		
	}
	
	
	
	private boolean ValidarPrecio(String precio) {
        String exp = "^([0-9]{1,3}).([0-9]{2}$)";
        Pattern patron = Pattern.compile(exp);
        Matcher coincide = patron.matcher(precio);
        return coincide.matches();
    }
	
	
}
