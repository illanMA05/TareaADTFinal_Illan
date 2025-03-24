package com.luisdbb.tarea3AD2024base.controller;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Paradas;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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
	
	@Autowired
	private ParadaService paradaService;
	
	@Lazy
	@Autowired
	private StageManager stageManager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblResponsable.setText(Sesion.sesion.getUsuario());
		
		Paradas p =paradaService.findByResponsable(Sesion.sesion.getUsuario());
		
		lblNomParada.setText(p.getNombre());
		lblRegion.setText(p.getRegion()+"");
		
	}
	
	@FXML
	public void clickBtnVolver(ActionEvent event) throws IOException{
		stageManager.switchScene(FxmlView.ADMINPARADA);
	}
}
