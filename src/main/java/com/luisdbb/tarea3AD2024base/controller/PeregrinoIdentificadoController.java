package com.luisdbb.tarea3AD2024base.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.http.HttpHeaders;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.http.ResponseEntity;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Estancias;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.PeregrinoParadas;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
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
import javafx.scene.control.Label;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;


@Controller
//@RestController
//@RequestMapping("/report")
public class PeregrinoIdentificadoController implements Initializable{
	
	@FXML
	private Button btnCerrarSesion;
	
	@FXML
	private Button btnEditar;
	
	@FXML
	private Button btnAyuda;
	
	@FXML
	private Button btnInforme;
	
	@FXML
	private Button btnExportarDatos;
	
	@FXML
	private Label lblNombre;
	
	@FXML
	private Label lblEmail;
	
	@FXML
	private Label lblNacionalidad;
	
	@Autowired
	private PeregrinoService pereService;
	
	
	
	@Autowired
	private PeregrinoParadaService ppService;
	
	
	@Lazy
	@Autowired
	private StageManager stageManager;
	
	
	
	
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		Peregrino p = pereService.findByNombreUsu(Sesion.sesion.getUsuario());
		
		lblNombre.setText(p.getNombre());
		lblEmail.setText(p.getEmail());
		lblNacionalidad.setText(p.getNacionalidad());

	}
	
	@FXML
	public void clickBtnEditar(ActionEvent event) {
		Alert mensa = new Alert(Alert.AlertType.WARNING);
		mensa.setTitle("FUNCION NO DISPONIBLE");
		mensa.setContentText("ESTA FUNCION SERA IMPLEMENTADA A FUTURO");
		mensa.showAndWait();
	}
	
		@FXML
		public void exportarCarnet(ActionEvent event)throws IOException {
			
			//crear un peregrino completo
			Peregrino p = pereService.findByNombreUsu(Sesion.sesion.getUsuario());
			
			try {
			DocumentBuilderFactory fabricaConstructorDocumento = DocumentBuilderFactory.newInstance();
	        DocumentBuilder constructorDocumento = fabricaConstructorDocumento.newDocumentBuilder();
	        DOMImplementation implementacion = constructorDocumento.getDOMImplementation();
	        
	        
	        Document documento = implementacion.createDocument(null, p.getNombre()+"carnet", null);
	        Element carnet = documento.getDocumentElement();
	        
	        //CREAMOS TODAS LAS ETIQUETAS Y VALORES
	        Element id,fechaexp,peregrino,nombre,nacionalidad,hoy,distanciaTotal,paradas,parada,orden,nombreParada,region,estancias,estancia,idEstancia,fecha,paradaEs,vip;
	        Text idV,fechaexpV,nombreV,nacionalidadV,hoyV,distanciaTotalV,ordenV,nombreParadaV,regionV,idEstanciaV,fechaV,paradaEsV;
	        
	        //AÑADIMOS TODAS LAS ETQUETAS AL CARNET
	        id = documento.createElement("id");
	        carnet.appendChild(id);
	        idV= documento.createTextNode(p.getIdP().toString());
	        id.appendChild(idV);
	        
	        
	        fechaexp = documento.createElement("fechaexp");
	        carnet.appendChild(fechaexp);
	        fechaexpV= documento.createTextNode(p.getCarnet().getFechaExp().toString());
	        fechaexp.appendChild(fechaexpV);
	        
	        
	        peregrino = documento.createElement("peregrino");
	        carnet.appendChild(peregrino);
	       
	        nombre = documento.createElement("nombre");
	        peregrino.appendChild(nombre);
	        nombreV= documento.createTextNode(p.getNombre());
	        nombre.appendChild(nombreV);
	        
	        nacionalidad = documento.createElement("nacionalidad");
	        peregrino.appendChild(nacionalidad);
	        nacionalidadV= documento.createTextNode(p.getNacionalidad());
	        nacionalidad.appendChild(nacionalidadV);
	        
	        hoy = documento.createElement("hoy");
	        carnet.appendChild(hoy);
	        hoyV= documento.createTextNode(LocalDate.now().toString());
	        hoy.appendChild(hoyV);
	        
	        distanciaTotal = documento.createElement("distanciaTotal");
	        carnet.appendChild(distanciaTotal);
	        distanciaTotalV= documento.createTextNode(p.getCarnet().getDistancia()+"");
	        distanciaTotal.appendChild(distanciaTotalV);
	        
	        paradas = documento.createElement("paradas");
	        carnet.appendChild(paradas);
	        
	        int orde=1;
	        for(PeregrinoParadas pp: ppService.findByPeregrinoEquals(p)) {
	        
	        parada = documento.createElement("parada");
	        paradas.appendChild(parada);
	        
	        orden = documento.createElement("orden");
	        parada.appendChild(orden);
	        
	        ordenV= documento.createTextNode(orde+"");
	        orden.appendChild(ordenV);
	        orde++;
	        
	        nombreParada = documento.createElement("nombre");
	        parada.appendChild(nombreParada);
	        nombreParadaV= documento.createTextNode(pp.getParadas().getNombre());
	        nombreParada.appendChild(nombreParadaV);
	        
	        region = documento.createElement("region");
	        parada.appendChild(region);
	        regionV= documento.createTextNode(pp.getParadas().getRegion()+"");
	        region.appendChild(regionV);
	        }
	      
	        estancias = documento.createElement("estancias");
	        carnet.appendChild(estancias);
	        

	        for(Estancias es: p.getEstancias()) {
	        
	        estancia = documento.createElement("estancia");
	        estancias.appendChild(estancia);
	        
	        idEstancia = documento.createElement("id");
	        estancia.appendChild(idEstancia);
	        idEstanciaV= documento.createTextNode(es.getIdE().toString());
	        idEstancia.appendChild(idEstanciaV);
	        
	        fecha = documento.createElement("fecha");
	        estancia.appendChild(fecha);
	        fechaV= documento.createTextNode(es.getFecha().toString());
	        fecha.appendChild(fechaV);
	        
	        paradaEs = documento.createElement("parada");
	        estancia.appendChild(paradaEs);
	        paradaEsV= documento.createTextNode(es.getParadaE().getNombre());
	        paradaEs.appendChild(paradaEsV);
	        
	        if(es.isVip()) {
	        	
	        	vip = documento.createElement("vip");
	            estancia.appendChild(vip);
	        	
	        }
	        }
	        
	        
	        Source fuente = new DOMSource(documento);
	        String nomFichero = (".\\src\\main\\resources\\"+p.getNombreUsuario()+"_peregrino.xml");
	        File fichero = new File (nomFichero);
	        Result resultado = new StreamResult(fichero);
	        TransformerFactory fabricaTransformador= TransformerFactory.newInstance();
	        Transformer transformador = fabricaTransformador.newTransformer();
	        transformador.transform(fuente, resultado);
	        
	        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
			mensaje.setTitle("CARNET EXPORTADO");
			mensaje.setContentText("EL CARNET SE EXPORTO CORRECTAMENTE, PUEDE VERLO EN LA CARPETA src/main/resources");
			mensaje.showAndWait();
	        
			}
			catch (ParserConfigurationException ex) {
	            System.out.println("Error: " + ex.getMessage());
	        } catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	

}
