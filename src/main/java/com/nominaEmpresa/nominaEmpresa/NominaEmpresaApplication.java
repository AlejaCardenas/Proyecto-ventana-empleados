package com.nominaEmpresa.nominaEmpresa;

import com.nominaEmpresa.nominaEmpresa.Interfaz.VentanaCalculoNomina;
import jakarta.xml.bind.JAXBException;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class NominaEmpresaApplication {

	public static void main(String[] args){
		new VentanaCalculoNomina();
	}
}
