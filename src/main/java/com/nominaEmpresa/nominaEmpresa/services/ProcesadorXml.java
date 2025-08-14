package com.nominaEmpresa.nominaEmpresa.services;

import com.nominaEmpresa.nominaEmpresa.model.DocumentoSalida;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.IOException;

public class ProcesadorXml {
    private final File archivo;
    private final JAXBContext context;

    public ProcesadorXml() throws JAXBException {
        this.archivo = new File("BaseDatosNominaEmpleados.xml");
        if(!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("No se pudo crear el archivo XML", e);
            }
        }
        this.context = JAXBContext.newInstance(DocumentoSalida.class);
    }

    public DocumentoSalida leerDoc() {
        Unmarshaller unmarshaller = null;
        DocumentoSalida documento = null;
        try {
            unmarshaller = context.createUnmarshaller();
            documento = (DocumentoSalida) unmarshaller.unmarshal(this.archivo);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return documento;
    }

    public void escribirDoc(DocumentoSalida documento) {
        Marshaller marshaller;
        try {
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(documento, this.archivo);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
