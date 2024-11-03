/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Uranus;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author takina
 */
public class ResolutorSolucion {
    
    public ResolutorSolucion() {
        this.resolutor = new Resolutor();
        this.intentos = new ArrayList<Intento>();
    }

    public void setResolutor(String[] datosResolutor)
    {
        String id_Facultad = datosResolutor[0] != null ? datosResolutor[0] : "";
        String cedula = datosResolutor[1] != null ? datosResolutor[1] : "";
        String nombres = datosResolutor[2] != null ? datosResolutor[2] : "";
        String apellidos = datosResolutor[2] != null ? datosResolutor[2] : "";
        int edad = Integer.parseInt(datosResolutor[3] != null ? datosResolutor[3] : "-1");
        String sexo = datosResolutor[4] != null ? datosResolutor[4] : "";
        int semestre = Integer.parseInt(datosResolutor[5] != null ? datosResolutor[5] : "-1");
               
        // asigna los datos del resolutor
        this.resolutor.setId_Facultad(id_Facultad);
        this.resolutor.setCedula(cedula);
        this.resolutor.setNombres(nombres);
        this.resolutor.setApellidos(apellidos);
        this.resolutor.setEdad(edad);
        this.resolutor.setSexo(sexo);
        this.resolutor.setSemestre(semestre);
    }

    public void setIntentos(ArrayList<String[]> sequence) {        
        NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);        
        for (String[] next : sequence) {
//            float cercania = Float.parseFloat(next[2]);
//            float velocidad = Float.parseFloat(next[3]);
//            float tiempo = Float.parseFloat(next[4]);
            try {
                float cercania = format.parse(next[2]).floatValue();
                float velocidad = format.parse(next[3]).floatValue();
                float tiempo = format.parse(next[4]).floatValue();
                Intento intento = new Intento(tiempo, velocidad, cercania);
                intentos.add(intento);
            } catch (ParseException ex) {
                Logger.getLogger(ResolutorSolucion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void calcularControl(){
        AnalisisFacade facade = new AnalisisFacade();
        
        this.transicionesControl = facade.getTransicionesControl(intentos);
        this.segmentosControl    = facade.getSegmentosControl(transicionesControl);
        this.trayectosControl    = facade.getTrayectosControl(segmentosControl);
        this.trayectoriaControl  = facade.getTrayectoriaControl(trayectosControl);     
    }
    
    public void calcularCovariacion(){
        AnalisisFacade facade = new AnalisisFacade();
        
        this.transicionesCovariacion = facade.getTransicionesCovariacion(intentos);
        this.segmentosCovariacion    = facade.getSegmentosCovariacion(transicionesCovariacion);
        this.trayectosCovariacion    = facade.getTrayectosCovariacion(segmentosCovariacion);
        this.trayectoriaCovariacion  = facade.getTrayectoriaCovariacion(trayectosCovariacion);            
    }    

    public Resolutor getResolutor() {
        return resolutor;
    }

    public ArrayList<Intento> getIntentos() {
        return intentos;
    }

    public String[] getTransicionesControl() {
        return transicionesControl;
    }

    public ArrayList<ASegmento> getSegmentosControl() {
        return segmentosControl;
    }

    public ArrayList<ATrayecto> getTrayectosControl() {
        return trayectosControl;
    }

    public ATrayectoria getTrayectoriaControl() {
        return trayectoriaControl;
    }

    public String[] getTransicionesCovariacion() {
        return transicionesCovariacion;
    }

    public ArrayList<ASegmento> getSegmentosCovariacion() {
        return segmentosCovariacion;
    }

    public ArrayList<ATrayecto> getTrayectosCovariacion() {
        return trayectosCovariacion;
    }

    public ATrayectoria getTrayectoriaCovariacion() {
        return trayectoriaCovariacion;
    }
    
    //---------------------------------------------
    public Resolutor resolutor;
    public ArrayList<Intento> intentos;              // secuencia de intentos del resolutor
    
    // Dimension control
    public String[] transicionesControl;
    public ArrayList<ASegmento> segmentosControl;
    public ArrayList<ATrayecto> trayectosControl;
    public ATrayectoria trayectoriaControl;

    // Dimension covariacion
    public String[] transicionesCovariacion;    
    public ArrayList<ASegmento> segmentosCovariacion;
    public ArrayList<ATrayecto> trayectosCovariacion;
    public ATrayectoria trayectoriaCovariacion;
    
}
