package com.nominaEmpresa.nominaEmpresa.model;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name= "documentoSalida")
@XmlAccessorType(XmlAccessType.FIELD)

public class DocumentoSalida {
    @XmlElementWrapper(name="empleadosNomina")
    @XmlElement(name="salidaWrapper")
    private List<SalidaWrapper> empleadosNomina = new ArrayList<>();


    public void setEmpleadosNomina(List<SalidaWrapper> empleadosNomina) {
        this.empleadosNomina = empleadosNomina;
    }
    public List<SalidaWrapper> getEmpleadosNomina() {
        return empleadosNomina;
    }
}
