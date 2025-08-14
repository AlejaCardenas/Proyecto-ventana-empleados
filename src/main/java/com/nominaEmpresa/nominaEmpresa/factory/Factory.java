package com.nominaEmpresa.nominaEmpresa.factory;

import com.nominaEmpresa.nominaEmpresa.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Factory {
    public Factory(){}

    public static DocumentoEntrada crearEmpleadosDto(){
        Direccion direccion = new Direccion();
        direccion.setResidencia("Cra 9 # 33 -44");
        direccion.setBarrio("Mirador Polo");
        direccion.setCiudad("Cauca");
        LocalDate fechaNac = LocalDate.of(1989,11,15);
        Banda banda = Banda.A6;
        Cargo cargo = Cargo.DESARROLLADOR_FULLSTACK;
        DatosNomina datosNomina = new DatosNomina();
        datosNomina.setHorasT(160);
        datosNomina.setValHora(60000);
        datosNomina.setDiasV(4);
        datosNomina.setDiasI(0);
        DatosEmpleados empleado1 = new DatosEmpleados("1033718747","Juan Carlos","Fuyo Gonzalez","3144071574","juanf67@gmail.com",direccion,fechaNac, cargo, "Desarrollador web",banda);
        EntradaWrapper entradaWrapper = new EntradaWrapper();
        entradaWrapper.setDatosEmpleados(empleado1);
        entradaWrapper.setDatosNomina(datosNomina);

        Direccion direccion1 = new Direccion();
        direccion1.setResidencia("Cra 8 # 66 - 55");
        direccion1.setBarrio("Sacarias");
        direccion1.setCiudad("Cali");
        LocalDate fechaNac1 = LocalDate.of(2002,7,24);
        Banda banda1 = Banda.A4;
        Cargo cargo1 = Cargo.BACK_END_JUNIOR;
        DatosNomina datosNomina1 = new DatosNomina();
        datosNomina1.setHorasT(170);
        datosNomina1.setValHora(45000);
        datosNomina1.setDiasV(7);
        datosNomina1.setDiasI(2);
        DatosEmpleados empleado2 = new DatosEmpleados("1000807021","Maria Alejandra","Cardenas Anaya","3117792692","maca33@gmail.com",direccion1,fechaNac1,cargo1,"Desarrollador web",banda1);
        EntradaWrapper entradaWrapper2 = new EntradaWrapper();
        entradaWrapper2.setDatosEmpleados(empleado2);
        entradaWrapper2.setDatosNomina(datosNomina1);

        Direccion direccion2 = new Direccion();
        direccion2.setResidencia("Cra 4 # 77 - 22");
        direccion2.setBarrio("Mirador Barcelona");
        direccion2.setCiudad("Medellin");
        LocalDate fechaNac2 = LocalDate.of(2000,9,22);
        Banda banda2 = Banda.A1;
        Cargo cargo2 = Cargo.ASEADORA;
        DatosNomina datosNomina2 = new DatosNomina();
        datosNomina2.setHorasT(120);
        datosNomina2.setValHora(20000);
        datosNomina2.setDiasV(0);
        datosNomina2.setDiasI(2);
        DatosEmpleados empleado3 = new DatosEmpleados("1037778927","Miriam","Ceballo  Carmona","3149996754","miri7877@gmail.com",direccion2,fechaNac2,cargo2,"Servicios Generales",banda2);
        EntradaWrapper entradaWrapper3 = new EntradaWrapper();
        entradaWrapper3.setDatosEmpleados(empleado3);
        entradaWrapper3.setDatosNomina(datosNomina2);

        DocumentoEntrada documentoEntrada = new DocumentoEntrada();
        List<EntradaWrapper> empleados = new ArrayList<>();
        empleados.add(entradaWrapper);
        empleados.add(entradaWrapper2);
        empleados.add(entradaWrapper3);
        documentoEntrada.setEmpleados(empleados);
        return documentoEntrada;
    }
}
