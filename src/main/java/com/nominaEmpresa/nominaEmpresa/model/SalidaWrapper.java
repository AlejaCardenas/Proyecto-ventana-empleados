package com.nominaEmpresa.nominaEmpresa.model;

import com.nominaEmpresa.nominaEmpresa.factory.BonificacionBanda;
import com.nominaEmpresa.nominaEmpresa.factory.ValorNomina;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "salidaWrapper")
@XmlAccessorType(XmlAccessType.FIELD)

public class SalidaWrapper {
    @XmlElement(name = "datosEmpleado")
    DatosEmpleados datosEmpleados;

    @XmlElement(name = "valorNomina")
    ValorNomina valorNomina;

    @XmlElement(name = "bonificacionBanda")
    BonificacionBanda bonificacionBanda;
    public SalidaWrapper(){

    }
    public SalidaWrapper(DatosEmpleados datosEmpleados, ValorNomina valorNomina) {
        this.datosEmpleados = datosEmpleados;
        this.valorNomina = valorNomina;
    }

    public DatosEmpleados getDatosEmpleados() {
        return datosEmpleados;
    }

    public void setDatosEmpleados(DatosEmpleados datosEmpleados) {
        this.datosEmpleados = datosEmpleados;
    }

    public ValorNomina getValorNomina() {
        return valorNomina;
    }

    public void setValorNomina(ValorNomina valorNomina) {
        this.valorNomina = valorNomina;
    }

    public void setBonificacionBanda(BonificacionBanda bonificacionBanda) {
        this.bonificacionBanda = bonificacionBanda;
    }

    public BonificacionBanda getBonificacionBanda() {
        return bonificacionBanda;
    }
}
