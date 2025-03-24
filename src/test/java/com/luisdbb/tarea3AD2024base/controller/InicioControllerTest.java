package com.luisdbb.tarea3AD2024base.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.services.CredencialesService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



@ExtendWith(MockitoExtension.class)
public class InicioControllerTest {
	
	@InjectMocks 
	private InicioController iniController;
	
	@Mock
	private TextField usuario;
	
	@Mock
	private TextField contrasenia;
	
	@Mock
	private PasswordField pswContrasenia;
	
	@Mock 
	private StageManager stageManager;
	
	
	@Mock
	private CredencialesService cerdenService;
	
	
	@Mock
	private Alert alerta;
	
	@Mock
	private Label errorLabel;
	
	@Mock
	private Stage alertStage;

	
	@BeforeAll
	public static void initJfx() {
		Platform.startup(() ->{
			
		});
	}
	
	@BeforeEach
	public void setUp() {
		Platform.runLater(() ->{
		when(usuario.getText()).thenReturn("Susi");
		when(contrasenia.getText()).thenReturn("Bom");
		when(pswContrasenia.getText()).thenReturn("Bom");
		contrasenia.setVisible(true);
		when(alerta.showAndWait()).thenReturn(java.util.Optional.of(ButtonType.OK));
		when(cerdenService.perfilCredenciales(anyString())).thenReturn(1);
		});
	}
	
	@Test
	public void  testInicioSesion_Correcto() {
		Platform.runLater(() ->{
		try {
			iniController.clickBtnAcceder(mock(ActionEvent.class));
			
			
			verify(cerdenService, times(1)).perfilCredenciales("Susi");
			verify(stageManager, times(1)).switchScene(FxmlView.ADMINPARADA);
		} catch (IOException e) {
			e.printStackTrace();
		}
		});
	}
	
	@Test
	public void testInicioSesion_Incorrecto() {
		Platform.runLater(() ->{
		try {
			//simulamos el nombre vacio
			when(usuario.getText()).thenReturn("");
			iniController.clickBtnAcceder(mock(ActionEvent.class));
			
			verify(cerdenService, times(1)).perfilCredenciales("");
			verify(stageManager, never()).switchScene(FxmlView.ADMINPARADA);
		} catch (IOException e) {
			e.printStackTrace();
		}	
		});
		
	}
}
