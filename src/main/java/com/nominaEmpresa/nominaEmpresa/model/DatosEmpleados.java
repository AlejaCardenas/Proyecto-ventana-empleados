package com.nominaEmpresa.nominaEmpresa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.LocalDate;

@XmlRootElement(name = "datosEmpleados")
@XmlAccessorType(XmlAccessType.FIELD)

public class DatosEmpleados {
    @XmlElement(name = "id")
    private String id;
    @XmlElement(name ="nombre")
    private String nombre;
    @XmlElement(name = "apellido")
    private String apellido;
    @XmlElement(name = "telefono")
    private String telefono;
    @XmlElement(name = "correo")
    private String correo;
    @XmlElement(name = "direccion")
    private Direccion direccion;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @XmlElement(name = "fechaDeNac")
    private LocalDate fechaDeNac;
    @XmlElement(name = "cargo")
    private Cargo cargo;
    @XmlElement(name = "area")
    private String area;
    @XmlElement(name = "banda")
    private Banda banda;
    public DatosEmpleados(){}

    public DatosEmpleados(String id, String nombre, String apellido, String telefono, String correo, Direccion direccion, LocalDate fechaDeNac, Cargo cargo, String area, Banda banda) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.fechaDeNac = fechaDeNac;
        this.cargo = cargo;
        this.area = area;
        this.banda = banda;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public LocalDate getFechaDeNac() {
        return fechaDeNac;
    }

    public void setFechaDeNac(LocalDate fechaDeNac) {
        this.fechaDeNac = fechaDeNac;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Banda getBanda() {
        return banda;
    }

    public void setBanda(Banda banda) {
        this.banda = banda;
    }

}
