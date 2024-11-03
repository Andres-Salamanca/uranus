/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Uranus;

import java.io.File;

/**
 *
 * @author takina
 */
public class Principal {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        //--------------------------------------------------------------------
        //--------------------------------------------------------------------
        String path = System.getProperty("user.dir");
        if (path.contains("dist"))
            path = path.replace(File.separator + "dist", "");
        String sDirectorio = path + File.separator + "data" + File.separator + "capture";
            
        Problema movimiento = new Problema("Problema de movimiento rectilineo");
        
        if ( movimiento.loadInformation( sDirectorio ) ) {
            movimiento.calcularSolucionControl();
            movimiento.calcularSolucionCovariacion();
            
            movimiento.writeResolutorIntentosExcelFile();
            movimiento.writeAllControlExcelFile();
            movimiento.writeAllCovariacionExcelFile();
            movimiento.writeConsolidadoControlExcelFile();
            
            // Falta guardar en el archivo los consolidados de control y covariación.
            // Tambien se pueden guardar las frecuencias de transiciones
            
            movimiento.printFrecuenciaTransicionesControl();
            movimiento.imprimirTrayectoriasControl();
            
            movimiento.printFrecuenciaTransicionesCovariacion();
            movimiento.imprimirTrayectoriasCovariacion();
        }
    }
}