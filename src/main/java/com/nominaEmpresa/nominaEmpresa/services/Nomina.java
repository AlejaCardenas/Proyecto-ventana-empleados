package com.nominaEmpresa.nominaEmpresa.services;

import com.nominaEmpresa.nominaEmpresa.factory.BonificacionBanda;
import com.nominaEmpresa.nominaEmpresa.factory.Factory;
import com.nominaEmpresa.nominaEmpresa.factory.ValorNomina;
import com.nominaEmpresa.nominaEmpresa.model.DocumentoEntrada;
import com.nominaEmpresa.nominaEmpresa.model.DocumentoSalida;
import com.nominaEmpresa.nominaEmpresa.model.EntradaWrapper;
import com.nominaEmpresa.nominaEmpresa.model.SalidaWrapper;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Nomina {
    private ProcesadorJson procesadorJson;
    private ProcesadorXml procesadorXml;

    public Nomina() throws JAXBException {
        crearJson();
    }

    private void crearJson() {
        this.procesadorJson = new ProcesadorJson();
        if (!procesadorJson.existeArchivo()) {
            DocumentoEntrada documentoEntrada = Factory.crearEmpleadosDto();
            this.procesadorJson.escribirDocumento(documentoEntrada);
        } else {
            System.out.println("El archivo Json ya existe");
        }
    }

    public void procesar() throws JAXBException {

        DocumentoEntrada documentoEntrada = this.procesadorJson.leerDocumento();
        List<EntradaWrapper> empleadosEtList = documentoEntrada.getListEmpleados();

        this.procesadorXml = new ProcesadorXml();
        DocumentoSalida documentoSalida = new DocumentoSalida();
        List<SalidaWrapper> empleadosConNomina = new ArrayList<>();

        for (EntradaWrapper empleado : empleadosEtList) {
            ValorNomina valorNomina = new ValorNomina(empleado);
            SalidaWrapper salida = new SalidaWrapper(empleado.getDatosEmpleados(), valorNomina);
            BonificacionBanda bonificacionBanda = new BonificacionBanda(salida);
            salida.setBonificacionBanda(bonificacionBanda);
            empleadosConNomina.add(salida);

        }
        documentoSalida.setEmpleadosNomina(empleadosConNomina);
        this.procesadorXml.escribirDoc(documentoSalida);
    }
}



