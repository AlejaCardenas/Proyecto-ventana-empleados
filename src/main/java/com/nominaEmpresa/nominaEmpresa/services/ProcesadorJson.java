package com.nominaEmpresa.nominaEmpresa.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nominaEmpresa.nominaEmpresa.model.DocumentoEntrada;

import java.io.File;
import java.io.IOException;

public class ProcesadorJson {
    private File procesadorJson;
    private ObjectMapper objectMapper;

    public ProcesadorJson(){
        this.procesadorJson = new File("BaseDatosEmpleados.json");
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT); //
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public boolean existeArchivo(){
        boolean existe = false;
        if(procesadorJson.exists()){
            existe = true;
        }
        return existe;
    }

    public DocumentoEntrada leerDocumento(){
        DocumentoEntrada documentoEntrada = null;
        try{
            documentoEntrada = objectMapper.readValue(this.procesadorJson, DocumentoEntrada.class);
        }catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException("Error al leer el documento JSON", e);
        }
        return documentoEntrada;
    }

 public void escribirDocumento(DocumentoEntrada documentoEntrada){
        try{
            this.objectMapper.writerWithDefaultPrettyPrinter().writeValue(this.procesadorJson, documentoEntrada);
        }catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException("Error al leer el documento JSON", e);
        }
 }

}
