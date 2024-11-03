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
public class ImpresionDatos {

    
    //
    //
    public void printIntentos(ArrayList<Intento> intentos) {
        System.out.println("Longitud = " + intentos.size() + " Intentos = ");
        for (int k = 0; k < intentos.size(); k++) {
            System.out.print(intentos.get(k).getX() + "\t");
        }
        System.out.println();

        for (int k = 0; k < intentos.size(); k++) {
            System.out.print(intentos.get(k).getY() + "\t");
        }
        System.out.println();
        
        for (int k = 0; k < intentos.size(); k++) {
            System.out.print(intentos.get(k).getZ() + "\t");
        }
        System.out.println();
    }
    //------------------------------------------------------------
    //------------------------------------------------------------
    public void printTransiciones(String[] transiciones) {
        System.out.print("Longitud = " + transiciones.length + " Transiciones = ");
        for (String transicion : transiciones) {
            System.out.print(transicion);
        }
        System.out.println();
    }

    //------------------------------------------------------------
    //------------------------------------------------------------
    public void printSegmentos(ArrayList<ASegmento> segmentos) {
        System.out.print("Longitud = " + segmentos.size() + " Segmentos = ");
        for (ASegmento segmento : segmentos) {
            System.out.print(segmento.getLongitud() + "-");
            System.out.print(segmento.getNombre() + ", ");
        }
        System.out.println();
    }    

    //------------------------------------------------------------
    //------------------------------------------------------------
    public void printTrayectos(ArrayList<ATrayecto> trayectos) {
        System.out.print("Longitud = " + trayectos.size() + " Trayectos = ");
        for (ATrayecto trayecto : trayectos) {
            System.out.print(trayecto.getLongitud() + "-");
            System.out.print(trayecto.getNombre() + ", ");
        }
        System.out.println();
    }  

    //------------------------------------------------------------
    //------------------------------------------------------------
    public void printTrayectoria(ATrayectoria trayectoria) {
        if (trayectoria != null)
            System.out.println("Longitud = " + trayectoria.getLongitud() + " Trayectoria = " + trayectoria.getNombre());
        else
            System.out.println("------------------------------------");
    }

    //------------------------------------------------------------
    //------------------------------------------------------------
    public void printFrecuenciaTransiciones(int [][] frecuencias, int tamano) {
        // imprimir la tabla de frecuencias
        char fila = 'a';

        for (int i = 0; i < tamano; i++) {
            System.out.print("\t");
            System.out.print(fila++);
        }
        System.out.println();

        fila = 'a';
        for (int i = 0; i < tamano; i++) {
            System.out.print(fila++);

            for (int j = 0; j < tamano; j++) {
                System.out.print("\t");
                System.out.print(frecuencias[i][j]);
            }
            System.out.println();
        }
    }


    public void printAcumuladoTrayectoriasControl(CategoriasTrayectorias categorias, int tamano) {        
        System.out.println("TRAYECTORIAS SEGUN LA DIMENSION CONTROL");

        for (ATrayectoria trayectoria : categorias.control) {
            System.out.printf("%-40s : ", trayectoria.getNombre());
            System.out.printf("%10d : ", trayectoria.getLongitud());            
            System.out.printf ("%.2f", (100.0*trayectoria.getLongitud()/tamano));
            System.out.println("%");
        }
    }
    
    public void printAcumuladoTrayectoriasCovariacion(CategoriasTrayectorias categorias, int tamano) {        
        System.out.println("TRAYECTORIAS SEGUN LA DIMENSION COVARIACION");

        for (ATrayectoria trayectoria : categorias.covariacion) {
            System.out.printf("%-40s : ", trayectoria.getNombre());
            System.out.printf("%10d : ", trayectoria.getLongitud());            
            System.out.printf ("%.2f", (100.0*trayectoria.getLongitud()/tamano));
            System.out.println("%");
        }
    }    
        
}
