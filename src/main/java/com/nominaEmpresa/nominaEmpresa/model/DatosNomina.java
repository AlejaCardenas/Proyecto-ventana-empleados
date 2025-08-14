package com.nominaEmpresa.nominaEmpresa.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="datosNomina")
@XmlAccessorType(XmlAccessType.FIELD)
public class DatosNomina {
    @XmlElement(name = "valHora")
    private long valHora;
    @XmlElement(name = "horasT")
    private int horasT;
    @XmlElement(name = "diasV")
    private int diasV;
    @XmlElement(name = "diasI")
    private int diasI;

    public long getValHora() {
        return valHora;
    }

    public void setValHora(long valHora) {
        this.valHora = valHora;
    }

    public int getHorasT() {
        return horasT;
    }

    public void setHorasT(int horasT) {
        this.horasT = horasT;
    }

    public int getDiasV() {
        return diasV;
    }

    public void setDiasV(int diasV) {
        this.diasV = diasV;
    }

    public int getDiasI() {
        return diasI;
    }

    public void setDiasI(int diasI) {
        this.diasI = diasI;
    }
}
