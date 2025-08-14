package com.nominaEmpresa.nominaEmpresa.factory;

import com.nominaEmpresa.nominaEmpresa.model.Banda;
import com.nominaEmpresa.nominaEmpresa.model.DatosNomina;
import com.nominaEmpresa.nominaEmpresa.model.EntradaWrapper;
import com.nominaEmpresa.nominaEmpresa.model.SalidaWrapper;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "bonificacionBanda")
@XmlAccessorType(XmlAccessType.FIELD)

public class BonificacionBanda {
    private Banda banda;
    private long valorNom;
    private long valorBanda;

    public BonificacionBanda() {}

    public BonificacionBanda(SalidaWrapper bonificacionEmpleado){
         ValorNomina valorNomina = bonificacionEmpleado.getValorNomina();
         this.valorNom = valorNomina.valorNomina();
         this.banda = bonificacionEmpleado.getDatosEmpleados().getBanda();
         this.valorBanda = valorBonificacionesB();
    }

   private long valorBonificacionesB(){
        long bonificacionBanda = 0;
        switch (banda){
            case A1:
                bonificacionBanda = Math.round((this.valorNom * 12) * 0.03);
                break;
            case A2:
                bonificacionBanda = Math.round((this.valorNom * 12) * 0.06);
                break;
            case A3:
                bonificacionBanda = Math.round((this.valorNom * 12) * 0.09);
                break;
            case A4:
                bonificacionBanda = Math.round((this.valorNom * 12) * 0.12);
                break;
            case A5:
                bonificacionBanda = Math.round((this.valorNom * 12) * 0.15);
                break;
            case A6:
                bonificacionBanda = Math.round((this.valorNom * 12) * 0.18);
                break;
        }
        return bonificacionBanda;
   }

    public long getValorBanda() {
        return valorBanda;
    }
}
