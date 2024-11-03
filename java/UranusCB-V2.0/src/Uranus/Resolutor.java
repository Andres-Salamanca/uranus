/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Uranus;

/**
 *
 * @author takina
 */
public class Resolutor {

    public String getId_Facultad() {
        return id_Facultad;
    }

    public void setId_Facultad(String id_Facultad) {
        this.id_Facultad = id_Facultad;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public Resolutor(String id_Facultad, String cedula, String nombres, String apellidos, int edad, String sexo, int semestre) {
        this.id_Facultad = id_Facultad;
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.edad = edad;
        this.sexo = sexo;
        this.semestre = semestre;
    }

    public Resolutor() {
    }
    
    public String id_Facultad;
    public String cedula;
    public String nombres;
    public String apellidos;
    public int edad;
    public String sexo;
    public int semestre;
                
}
