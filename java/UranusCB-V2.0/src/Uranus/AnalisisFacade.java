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
public class AnalisisFacade {
    
    public String[] getTransicionesControl(ArrayList<Intento> intentos){
        AnalisisControl control = new AnalisisControl();
        return control.getTransiciones(intentos);
    }
    
    public ArrayList<ASegmento> getSegmentosControl(String[] transiciones) {
        AnalisisControl control = new AnalisisControl();
        return control.getSegmentos(transiciones);
    }
            
    public ArrayList<ATrayecto> getTrayectosControl(ArrayList<ASegmento> segmentos) {
        AnalisisControl control = new AnalisisControl();
        return control.getTrayectos(segmentos);
    }

    public ATrayectoria getTrayectoriaControl(ArrayList<ATrayecto> trayectos) {
        AnalisisControl control = new AnalisisControl();
        return control.getTrayectoria(trayectos);
    }
    
    //--------------------------------------------------------------------------
    public String[] getTransicionesCovariacion(ArrayList<Intento> intentos){
        AnalisisCovariaciones covariaciones = new AnalisisCovariaciones();
        return covariaciones.getTransiciones(intentos);
    }
    
    public ArrayList<ASegmento> getSegmentosCovariacion(String[] transiciones) {
        AnalisisCovariaciones covariaciones = new AnalisisCovariaciones();
        return covariaciones.getSegmentos(transiciones);
    }
            
    public ArrayList<ATrayecto> getTrayectosCovariacion(ArrayList<ASegmento> segmentos) {
        AnalisisCovariaciones covariaciones = new AnalisisCovariaciones();
        return covariaciones.getTrayectos(segmentos);
    }

    public ATrayectoria getTrayectoriaCovariacion(ArrayList<ATrayecto> trayectos) {
        AnalisisCovariaciones covariaciones = new AnalisisCovariaciones();
        return covariaciones.getTrayectoria(trayectos);
    }
    
}
