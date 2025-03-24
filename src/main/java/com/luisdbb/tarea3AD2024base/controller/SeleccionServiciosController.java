package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.db4o.ObjectContainer;
import com.db4o.ext.Db4oException;
import com.luisdbb.tarea3AD2024base.config.DataConnection;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.ConjuntoContratado;
import com.luisdbb.tarea3AD2024base.modelo.Paradas;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.Servicios_ConjContraService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

@Controller
public class SeleccionServiciosController implements Initializable{
	private ObjectContainer db = DataConnection.getInstance();
	
	@FXML
	private TableView<Servicio> tablaServicios;
	
	@FXML
	private RadioButton rbEfectivo;
	
	@FXML
	private RadioButton rbTarjeta;
	@FXML
	private RadioButton rbBizum;
	
	@FXML
	private TextArea txtExtras;
	
//	@FXML
//	private CheckBox checkEnvio;
	
	@FXML
	private TableColumn <String, String> clmPrecio;
	
	
	@FXML
	private TableColumn clmId;
	
	@FXML
	private TableColumn <String, String>clmNombre;
	
	@Autowired
	private ParadaService paradaService;

	@Lazy
	@Autowired
	private StageManager stageManager;
	
	@Autowired
	private Servicios_ConjContraService serConjService;
	

	
	public ObservableList<Servicio> servicios;
	
	public SellarAlojarseController seAlo;
	
	
	public void initialize(URL location, ResourceBundle resources) {
		
		// TODO Auto-generated method stub
		servicios = FXCollections.observableArrayList();
		
		tablaServicios.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		this.clmNombre.setCellValueFactory(new PropertyValueFactory<String, String>("Nombre"));
		this.clmId.setCellValueFactory(new PropertyValueFactory("Id"));
		this.clmPrecio.setCellValueFactory(new PropertyValueFactory<String, String>("Precio"));	
		
		Paradas p =paradaService.findByResponsable(Sesion.sesion.getUsuario());
		
		for(Servicio s : serConjService.findAllServicios()) {
			if(s.getId()==0L) {
				servicios.add(s);
			}
			if(s.getParadas().contains(p.getIdPa()) && s.getId()!=0L) {
				servicios.add(s);
			}
		}
		
		tablaServicios.setItems(servicios);
			
	}
	
	@FXML
	private void clickBtnAceptar(ActionEvent event) throws IOException{
		
		if(tablaServicios.getSelectionModel().getSelectedItems().size() == 0) {
			
			Alert mensa = new Alert(Alert.AlertType.CONFIRMATION);
			mensa.setTitle("SERVICIOS NOS SELECCIONADOS");
			mensa.setContentText("ESTÁ SEGURO DE QUE NO QUIERE CONTRATAR SERVICIOS?");
			Optional <ButtonType> opcion = mensa.showAndWait();
			if(opcion.isPresent() &&opcion.get().equals(ButtonType.OK)) {
			
				Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
				mensaje.setTitle("SERVICIOS NO CONTRATADOS");
				mensaje.setContentText("OPERACION REALIZADA CON EXITO, VOLVIENDO AL MENU...");
				mensaje.showAndWait();
				
				stageManager.switchScene(FxmlView.ADMINPARADA);
			}
		}
		else {
			
			Long id=0L;
    		List<ConjuntoContratado> ps2 = serConjService.findAllConjuntos();
    		 for (ConjuntoContratado ter : ps2) {
    		 id=ter.getId();
             }
    		 	id++;
    		 	double precioTotal = 0.00;
    		 	for(Servicio s : tablaServicios.getSelectionModel().getSelectedItems()) {
    		 		precioTotal += s.getPrecio();
    		 	}
    		 	char modoPago=' ';
    		 	if(rbBizum.isSelected())modoPago='B';
    		 	else if(rbTarjeta.isSelected())modoPago='T';
    		 	else modoPago='E';
    		 	
    		 	if(txtExtras.getText().isEmpty() || txtExtras.getText() == null) {
    		 		txtExtras.setText(" ");
    		 	}
    		 	List<Long> servi = new ArrayList<Long>();
    		 	for(Servicio s : tablaServicios.getSelectionModel().getSelectedItems()) {
    		 		servi.add(s.getId());
    		 	}
    		 	
    		 	ConjuntoContratado cj = new ConjuntoContratado(id,precioTotal,modoPago,txtExtras.getText(),SellarAlojarseController.estancia.getIdE(),servi);
    		 	

    		 	try {
    		 		db.store(cj);
    		 		db.commit();
    		 		
    		 		
    				
//    				if(checkEnvio.isSelected()) {
//    					cj.getContratar().add(0L);
//    					stageManager.switchScene(FxmlView.ENVIOACASA);
//    					
//    				}
    		 		
    		 		if(servi.contains(0L)) {
    		 			cj.getContratar().add(0L);
    					stageManager.switchScene(FxmlView.ENVIOACASA);
    		 		}
    				else {
    					
    					Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
        				mensaje.setTitle("SERVICIOS CONTRATADOS");
        				mensaje.setContentText("OPERACION REALIZADA CON EXITO, VOLVIENDO AL MENU...");
        				mensaje.showAndWait();
    					stageManager.switchScene(FxmlView.ADMINPARADA);
    				}
    				
    		 		
    		 	}catch (Db4oException ee) {
					 JOptionPane.showMessageDialog(null,ee.getMessage());
					 db.rollback();
					 }
    		 	
			
		}
		
	}

}
