package com.luisdbb.tarea3AD2024base.view;

import java.util.ResourceBundle;

public enum FxmlView {
	ADMIN {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("admin.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/AdminGeneral.fxml";
		}
	},
	LOGIN {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("login.title");
		}
		
		@Override
		public String getFxmlFile() {
			return "/fxml/Inicio.fxml";
		}
	},
	NUEVOPERE{

		@Override
		public String getTitle() {
			return getStringFromResourceBundle("registroPere.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/RegistroNuevoPere.fxml";
		}
		
	},
	VERENVIOS{
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("envios.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/VerEnviosRealizados.fxml";
		}
	},
	ADMINPARADA{
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("adminParada.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/AdminParada.fxml";
		}
		
	},
	PEREGRINOIDENTIFICADO{
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("pereIdentificado.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/PeregrinoIdentificado.fxml";
		}
	},
	NUEVAPARADA{
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("nuevaParada.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/NuevaParada.fxml";
		}
	},
	SELLAR{
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("sellar.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/SellarAlojarse.fxml";
		}
	},
	NUEVOSERVICIO{
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("nueServicio.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/NuevoServicio.fxml";
		}
	},
	EDITARSERVICIO{
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("editServicio.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/EditarServicio.fxml";
		}
	},
	EDITARSERVICIOSELECCIONADO{
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("editServicio.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/EditarServicioSeleccionado.fxml";
		}
	},
	SELECCIONARSERVICIOS{
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("seleccionServicios.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/SeleccionServicios.fxml";
		}
	},
	ENVIOACASA{
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("envioACasa.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/EnvioACasa.fxml";
		}
	},
	VERCARNETS{
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("verCarnets.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/VerCarnets.fxml";
		}
	}
	
	
	;

	public abstract String getTitle();

	public abstract String getFxmlFile();

	String getStringFromResourceBundle(String key) {
		return ResourceBundle.getBundle("Bundle").getString(key);
	}
}
