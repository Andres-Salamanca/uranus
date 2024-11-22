/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Uranus;

import java.util.ArrayList;

/**
 *
 * @author takina
 */
public class ATrayecto {
    //---------------------------
    //---------------------------
    private String nombre;
    private int longitud;

    //----------------------------------------------
    //----------------------------------------------
    public ATrayecto(String nombre, int longitud) {
        this.nombre = nombre;
        this.longitud = longitud;
    }

    //----------------------------------------------
    //----------------------------------------------
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }
}