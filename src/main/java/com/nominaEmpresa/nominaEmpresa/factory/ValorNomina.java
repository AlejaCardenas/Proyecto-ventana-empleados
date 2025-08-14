package com.nominaEmpresa.nominaEmpresa.factory;
import com.nominaEmpresa.nominaEmpresa.model.DatosNomina;
import com.nominaEmpresa.nominaEmpresa.model.EntradaWrapper;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "valorNomina")
@XmlAccessorType(XmlAccessType.FIELD)
public class ValorNomina {
    private long pagoTNomina;
    private long diasV;
    private long valorV;
    private long diasI;
    private long valorI;
    private long horasT;
    private long valorT;
    private long valorH;
    private long descuentoS_S;


    public ValorNomina(){}
    public ValorNomina(EntradaWrapper empleado){
        DatosNomina datosNomina = empleado.getDatosNomina();

        this.valorH = datosNomina.getValHora();
        this.diasV = datosNomina.getDiasV();
        this.valorV = valorVacaciones();
        this.diasI = datosNomina.getDiasI();
        this.valorI = valorIncapacidad();
        this.horasT = datosNomina.getHorasT();
        this.valorT = valorTrabajado();
        this.descuentoS_S = descuentoSeguridadSocial();
        this.pagoTNomina = valorNomina();
    }

    public long valorNomina(){
        pagoTNomina = calculoSalario() - descuentoSeguridadSocial();
        return pagoTNomina;
    }
    private long calculoSalario(){
        long horas = 8;
        long valorT,valorV,valorI,salario;

        valorT =valorTrabajado();
        valorV = valorVacaciones();
        valorI = valorIncapacidad();
        salario = valorT + valorV + valorI;
        return salario;
    }

    private  long valorTrabajado(){
        long totalT;
        totalT = this.horasT * this.valorH;

        return totalT;
    }
    private long valorVacaciones(){
        long totalV;
        long horas = 8;
        totalV = (this.diasV * horas) * valorH;

        return totalV;
    }
    private long valorIncapacidad() {
        long incapacidad, totalI;
        long horas = 8;
        incapacidad = (this.diasI * horas) * this.valorH;
        if(this.diasI <= 2) {
            totalI = incapacidad;
        }else if(this.diasI > 3 && this.diasI  <= 180) {
            totalI = Math.round(incapacidad * 0.6667);

        }else {
            totalI = 0;
        }
        return totalI;
    }
    private long descuentoSeguridadSocial(){
        long pension,salud, descSegSocial;
        pension = Math.round(calculoSalario() * 0.04);
        salud = Math.round(calculoSalario() * 0.04);
        descSegSocial = pension + salud;
        return descSegSocial;
        }
    }

