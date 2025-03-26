package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.luisdbb.tarea3AD2024base.services.EstanciasService;
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
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;

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
	private Button btnInforme;
	
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
	private EstanciasService estanciaService;
	
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

	
	public void generarInforme() {

		Paradas parada = paradaService.findByResponsable(Sesion.sesion.getUsuario());

		try {

		JasperDesign jasperDesign = new JasperDesign();

		jasperDesign.setName("Estadisticas " + parada.getNombre() + "_" + parada.getRegion());

		jasperDesign.setPageWidth(595);

		jasperDesign.setPageHeight(842);

		jasperDesign.setColumnWidth(515);

		jasperDesign.setColumnSpacing(0);

		jasperDesign.setLeftMargin(40);

		jasperDesign.setRightMargin(40);

		jasperDesign.setTopMargin(50);

		jasperDesign.setBottomMargin(50);



		// Parámetros

		JRDesignParameter paramInfo = new JRDesignParameter();

		paramInfo.setName("info");

		paramInfo.setValueClass(String.class);

		jasperDesign.addParameter(paramInfo);

		JRDesignParameter paramTotalPeregrinos = new JRDesignParameter();

		paramTotalPeregrinos.setName("totalPeregrinos");

		paramTotalPeregrinos.setValueClass(Integer.class);

		jasperDesign.addParameter(paramTotalPeregrinos);



		JRDesignParameter paramTotalEstancias = new JRDesignParameter();

		paramTotalEstancias.setName("totalEstancias");

		paramTotalEstancias.setValueClass(Integer.class);

		jasperDesign.addParameter(paramTotalEstancias);



		// Banda de detalles

		JRDesignBand detailBand = new JRDesignBand();

		detailBand.setHeight(100);



		JRDesignStaticText text0 = new JRDesignStaticText();

		text0.setText("Información de la parada:");

		text0.setX(20);

		text0.setY(0);

		text0.setWidth(200);

		text0.setHeight(20);

		detailBand.addElement(text0);

		JRDesignStaticText text1 = new JRDesignStaticText();

		text1.setText("Total de Peregrinos:");

		text1.setX(20);

		text1.setY(30);

		text1.setWidth(200);

		text1.setHeight(20);

		detailBand.addElement(text1);



		JRDesignStaticText text2 = new JRDesignStaticText();

		text2.setText("Total de Estancias:");

		text2.setX(20);

		text2.setY(60);

		text2.setWidth(200);

		text2.setHeight(20);

		detailBand.addElement(text2);



		JRDesignTextField textFieldInfo = new JRDesignTextField();

		textFieldInfo.setExpression(new JRDesignExpression("$P{info}"));

		textFieldInfo.setX(230);

		textFieldInfo.setY(0);

		textFieldInfo.setWidth(250);

		textFieldInfo.setHeight(20);

		detailBand.addElement(textFieldInfo);

		JRDesignTextField textFieldPeregrinos = new JRDesignTextField();

		textFieldPeregrinos.setExpression(new JRDesignExpression("$P{totalPeregrinos}"));

		textFieldPeregrinos.setX(230);

		textFieldPeregrinos.setY(30);

		textFieldPeregrinos.setWidth(250);

		textFieldPeregrinos.setHeight(20);

		detailBand.addElement(textFieldPeregrinos);



		JRDesignTextField textFieldEstancias = new JRDesignTextField();

		textFieldEstancias.setExpression(new JRDesignExpression("$P{totalEstancias}"));

		textFieldEstancias.setX(230);

		textFieldEstancias.setY(60);

		textFieldEstancias.setWidth(250);

		textFieldEstancias.setHeight(20);

		detailBand.addElement(textFieldEstancias);



		((JRDesignSection) jasperDesign.getDetailSection()).addBand(detailBand);



		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);



		// Obtener datos

		String info=mapearParada(parada);

		List<PeregrinoParadas> visitas = ppService.findByParadasEquals(parada);

		List<Long> peregrinos = new ArrayList<>();

		for (PeregrinoParadas v : visitas) {

		peregrinos.add(v.getPeregrino().getIdP());

		}

		Integer totalPeregrinos = peregrinos.size();

		Integer totalEstancias = estanciaService.findByParadaEEquals(parada).size();



		// Parámetros

		Map<String, Object> parameters = new HashMap<>();

		parameters.put("info", info);

		parameters.put("totalPeregrinos", totalPeregrinos);

		parameters.put("totalEstancias", totalEstancias);



		JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());



		String outputFile = "src/main/resources/informes/Estadisticas_" +

		parada.getNombre()+"_"+

		parada.getRegion()+".pdf";



		JasperExportManager.exportReportToPdfFile(print, outputFile);
		
		Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
		mensaje.setTitle("Informe generado con éxito");
		mensaje.setContentText("Se ha generado su informe exitosamente, lo podrá encontrar en la ruta"+outputFile);
		mensaje.showAndWait();

		System.out.println("Reporte generado: " + outputFile);

		} catch (Exception e) {

		e.printStackTrace();

		}

		}

	private String mapearParada(Paradas parada) {
		String ret = parada.getNombre()+"_"+parada.getRegion()+" Gestionada por :"+parada.getResponsable();
		return ret;
	}


	

}
