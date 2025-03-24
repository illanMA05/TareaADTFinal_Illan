package com.luisdbb.tarea3AD2024base.controller;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;

import com.db4o.ObjectContainer;
import com.luisdbb.tarea3AD2024base.config.DataConnection;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Estancias;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.PeregrinoParadas;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.services.PeregrinoParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class AdminGeneralController implements Initializable{
	private ObjectContainer db = DataConnection.getInstance();

	@FXML
	private Button btnCerrarSesion;
	
	@FXML
	private Button btnNuevoServicio;
	
	@FXML
	private Button btnEditarServicio;
	
	@FXML
	private Button btnAñadirParada;
	
	@FXML
	private Button btnAyuda;
	
	@Lazy
	@Autowired
	private StageManager stageManager;
	
	@Autowired
	private PeregrinoParadaService ppService;
	
	@Autowired
    private MongoTemplate mongoTemplate;
	
	@Autowired
	private PeregrinoService pereService;
	

	@Override
	public void initialize(java.net.URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	public void clickBtnAñadirParada(ActionEvent event) throws IOException {
		
		stageManager.switchScene(FxmlView.NUEVAPARADA);
	}
	
	@FXML
	public void clickBtnEditarServicio(ActionEvent event) throws IOException {
		List<Servicio> ser = null;
		ser = db.query(Servicio.class);

		if(ser.size()==0) {
			Alert mensaje = new Alert(Alert.AlertType.WARNING);
			mensaje.setTitle("NO EXISTEN SERVICIO");
			mensaje.setContentText("NO HAY SERVICIOS PARA EDITAR EN ESTOS MOMENTOS");
			mensaje.showAndWait();
		}else {
		stageManager.switchScene(FxmlView.EDITARSERVICIO);
		}
	}
	
	@FXML
	public void clickBtnNuevoServicio(ActionEvent event) throws IOException {
		
		stageManager.switchScene(FxmlView.NUEVOSERVICIO);
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
	private void clickBtnBackUp(ActionEvent event) throws IOException{
		Document docPeregrinos = new Document();
		String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm:ss"));
		String titulo = "BACKUP DE LOS PEREGRINOS (" + fecha + ")";

		docPeregrinos.append("TITULO", titulo);

		List<Document> listaPeres = new ArrayList<>();

		for (Peregrino p : pereService.findAll()) {

		    Document datosPere = new Document();

		    datosPere.append("IDENTIDICADOR : ", p.getIdP());
		    datosPere.append("NOMBRE DEL PEREGRINO : ", p.getNombre());
		    datosPere.append("NACIONALIDAD : ", p.getNacionalidad());
		    datosPere.append("EXPEDIDO EN : ", p.getCarnet().getInicio().getNombre());
		    datosPere.append("FECHA DE EXPEDICION : ", p.getCarnet().getFechaExp()
		            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		    datosPere.append("DISTANCIA TOTAL : ", p.getCarnet().getDistancia());
		    datosPere.append("NUMERO DE VIPS : ", p.getCarnet().getNvips());

		    List<Document> listaPara = new ArrayList<>();

		    for (PeregrinoParadas PP :ppService.findByPeregrinoEquals(p)) {
		        Document datosPara = new Document();

		        datosPara.append("NOMBRE DE LA PARADA : ", PP.getParadas().getNombre());
		        datosPara.append("REGION DE LA PARADA : ", PP.getParadas().getRegion());

		        listaPara.add(datosPara);
		    }

		    List<Document> listaEstan = new ArrayList<>();

		    for (Estancias estancia : p.getEstancias()) {
		        Document datosEstancia = new Document();

		        datosEstancia.append("IDENTIFICADOR : ", estancia.getIdE());
		        datosEstancia.append("NOMBRE DE LA PARADA : ", estancia.getParadaE().getNombre());
		        datosEstancia.append("FECHA DE LA ESTANCIA : ", estancia.getFecha()
		                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		        datosEstancia.append("VIP", estancia.isVip());

		        listaEstan.add(datosEstancia);

		    }

		    datosPere.append("LISTA PARADAS", listaPara);
		    datosPere.append("LISTA DE ESTANCIAS", listaEstan);

		    listaPeres.add(datosPere);

		}

		docPeregrinos.append("Peregrino", listaPeres);

		String nombreBackup = "Backup_Peregrinos_" + fecha;
		mongoTemplate.getCollection(nombreBackup).insertOne(docPeregrinos);
		
		Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
		mensaje.setTitle("BACKUP REALIZADO");
		mensaje.setContentText("BACK REALIZADO CORRECTAMENTE");
		mensaje.showAndWait();
		

	}
}
