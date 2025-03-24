package com.luisdbb.tarea3AD2024base.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Credenciales;
import com.luisdbb.tarea3AD2024base.modelo.Paradas;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.services.CarnetService;
import com.luisdbb.tarea3AD2024base.services.CredencialesService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

@ExtendWith(MockitoExtension.class)
public class RegistroNuevoPereControllerTest {

	@Mock
	private TextField txtUsuario;
	
	@Mock
	private TextField txtNombre;
	
	@Mock
	private TextField txtContra;
	
	@Mock
	private TextField txtContra2;
	
	@Mock
	private TextField txtEmail;
	
	@Mock
	private ChoiceBox<String> pais;
	
	@Mock
	private ChoiceBox<String> paradaIni;
	
	@Mock
	private Alert alerta;
	
	@Mock
	private CredencialesService cerdenService;
	
	@Mock
	private CarnetService carnetService;
	
	@Mock 
	private ParadaService paService;
	@Mock 
	private PeregrinoService peService;
	
	@Mock
	private RegistroNuevoPereController regContro;
	
	@BeforeAll
	public static void initJfx() {
		Platform.startup(() ->{
			
		});
	}
	
	@BeforeEach
	public void setUp() {
		Platform.runLater(() ->{
			
			//when(txtUsuario.getText()).thenReturn("PereNuevo");
			when(txtContra.getText()).thenReturn("PereNuevo");
			when(txtContra2.getText()).thenReturn("PereNuevo");
			when(txtEmail.getText()).thenReturn("pere@pere.com");
			when(pais.getValue()).thenReturn("España");
			when(paradaIni.getValue()).thenReturn("Parada12");
			when(cerdenService.CredencialesExistenPorNombre(anyString())).thenReturn(true);
			when(paService.findByNombre(anyString())).thenReturn(new Paradas("Parada1", 'a', "responsable"));
			
			Peregrino p = new Peregrino("España","PereNuevo", "PereNuevo", "pere@pere.com");
			//when(peService.save(any(Peregrino.class))).thenReturn(p);
			
			Paradas pIni = new Paradas("Parada12", 'c', "Respon");
			//when(paService.findByNombre(anyString())).thenReturn(pIni);	
			
			Credenciales creden = new Credenciales ("PereNuevo","PereNuevo", "peregrino");
			
//			creden.setPere(p);
//			p.setCreden(creden);
//			
//			Carnet c = new Carnet (0.0,LocalDate.now(),0);
//			p.setCarnet(c);
//			c.setIdPere(p);					
//			c.setInicio(pIni);
//			when(carnetService.actualizarEntidad(any(Carnet.class))).thenReturn(c);
//			
			
			when(alerta.showAndWait()).thenReturn(java.util.Optional.of(ButtonType.OK));
			
			
		});
		
	}
	
	@Test
	public void testRegistroNuevoPere_Correcto() {
		try {
			
			regContro.clickBtnAceptar(mock(ActionEvent.class));
			
			
		} catch (IOException e) {
	
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRegistroNuevoPere_Incorrecto() {
		when(txtNombre.getText()).thenReturn("");
		
		try {
			regContro.clickBtnAceptar(mock(ActionEvent.class));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
}
