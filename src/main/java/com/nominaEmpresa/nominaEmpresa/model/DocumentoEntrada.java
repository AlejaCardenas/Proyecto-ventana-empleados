package com.nominaEmpresa.nominaEmpresa.model;
import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name= "documentoEntrada")
@XmlAccessorType(XmlAccessType.FIELD)

public class DocumentoEntrada {
    @XmlElementWrapper(name="empleados")
    @XmlElement(name="entradaWrapper")
    private List<EntradaWrapper> empleados;


    public void setEmpleados(List<EntradaWrapper> empleados) {
        this.empleados = empleados;
    }
    public void setListEmpleados(List<EntradaWrapper> empleados) {
        this.empleados = empleados;
    }
    public List<EntradaWrapper> getListEmpleados() {
        return empleados;
    }
}