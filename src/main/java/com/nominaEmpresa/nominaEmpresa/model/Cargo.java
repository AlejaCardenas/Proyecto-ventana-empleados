package com.nominaEmpresa.nominaEmpresa.model;

public enum Cargo {
    DESARROLLADOR_FULLSTACK("Desarrollador Full-Stack"), FRONT_END_SENIOR("Front end senior"), FRONT_END_JUNIOR("Front end junior"), BACK_END_SENIOR("Back end senior"), BACK_END_JUNIOR("Back end junior"),ASEADORA("Aseadora");
    private final String descripcion;

    Cargo(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion; // Esto hace que JComboBox muestre la descripción
    }
}