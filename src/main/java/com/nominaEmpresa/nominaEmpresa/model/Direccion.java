package com.nominaEmpresa.nominaEmpresa.model;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import jdk.jfr.DataAmount;

@Data
@XmlRootElement(name = "direccion")
@XmlAccessorType(XmlAccessType.FIELD)

public class Direccion {
    @XmlElement(name = "residencia")
    private String residencia;

    @XmlElement(name = "barrio")
    private String barrio;

    @XmlElement(name = "ciudad")
    private String ciudad;

    public String getResidencia() {
        return residencia;
    }

    public void setResidencia(String residencia) {
        this.residencia = residencia;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
}
