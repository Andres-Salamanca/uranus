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
public class CategoriasTrayectorias {

    public CategoriasTrayectorias() {
        control  = new ArrayList<>();
        control.add(new ATrayectoria("SIN CONTROL", 0));
        control.add(new ATrayectoria("CONTROL NO CONSOLIDADO", 0));
        control.add(new ATrayectoria("CONTROL CONSOLIDADO", 0));
        control.add(new ATrayectoria("DESMEJORANTE", 0));
        control.add(new ATrayectoria("MEJORANTE CON INTERMITENCIA", 0));
        control.add(new ATrayectoria("MEJORANTE SIN INTERMITENCIA", 0));
        control.add(new ATrayectoria("OTRAS", 0));

        
        
        covariacion  = new ArrayList<>();
        covariacion.add(new ATrayectoria("SIN COVARIACION", 0));
        covariacion.add(new ATrayectoria("COVARIACION NO CONSOLIDADA", 0));
        covariacion.add(new ATrayectoria("COVARIACION CONSOLIDADA", 0));
        covariacion.add(new ATrayectoria("DESMEJORANTE", 0));
        covariacion.add(new ATrayectoria("MEJORANTE CON INTERMITENCIA", 0));
        covariacion.add(new ATrayectoria("MEJORANTE SIN INTERMITENCIA", 0));
        covariacion.add(new ATrayectoria("OTRAS", 0));     
                              
    }
    
    
    public ArrayList<ATrayectoria> control;
    public ArrayList<ATrayectoria> covariacion;
    
}
