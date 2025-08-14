package com.nominaEmpresa.nominaEmpresa.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="entradaWrapper")
@XmlAccessorType(XmlAccessType.FIELD)
public class EntradaWrapper {
    @XmlElement(name = "datosEmpleados")
    private DatosEmpleados datosEmpleados;
    @XmlElement(name = "datosNomina")
    private DatosNomina datosNomina;

    public EntradaWrapper(){
    }

    public EntradaWrapper(DatosEmpleados datosEmpleados, DatosNomina datosNomina) {
        this.datosEmpleados = datosEmpleados;
        this.datosNomina = datosNomina;
    }

    public DatosEmpleados getDatosEmpleados() {
        return datosEmpleados;
    }

    public void setDatosEmpleados(DatosEmpleados datosEmpleados) {
        this.datosEmpleados = datosEmpleados;
    }

    public DatosNomina getDatosNomina() {
        return datosNomina;
    }

    public void setDatosNomina(DatosNomina datosNomina) {
        this.datosNomina = datosNomina;
    }
}
