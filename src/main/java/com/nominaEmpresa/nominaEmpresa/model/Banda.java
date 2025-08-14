package com.nominaEmpresa.nominaEmpresa.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "banda")
@XmlAccessorType(XmlAccessType.FIELD)
public enum Banda{
        A1,A2,A3,A4,A5,A6
    }
