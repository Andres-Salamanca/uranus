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
public class MatrizTransiciones {

    //------------------------------------------------------------
    //------------------------------------------------------------    
    public int getPositionControl(String estado) {
        if (estado != null) {
            if (estado.equals("a")) {
                return 0;
            }
            if (estado.equals("b")) {
                return 1;
            }
            if (estado.equals("c")) {
                return 2;
            }
            if (estado.equals("d")) {
                return 3;
            }
            if (estado.equals("e")) {
                return 4;
            }
            if (estado.equals("f")) {
                return 5;
            }
            if (estado.equals("g")) {
                return 6;
            }
            if (estado.equals("h")) {
                return 7;
            }
            if (estado.equals("i")) {
                return 8;
            }
            if (estado.equals("j")) {
                return 9;
            }
            if (estado.equals("k")) {
                return 10;
            }
            if (estado.equals("l")) {
                return 11;
            }
            if (estado.equals("m")) {
                return 12;
            }
        }
        return -1;
    }

    //------------------------------------------------------------
    //------------------------------------------------------------    
    public int getPositionCovariacion(String estado) {
        if (estado != null) {
            if (estado.equals("a")) {
                return 0;
            }
            if (estado.equals("b")) {
                return 1;
            }
            if (estado.equals("c")) {
                return 2;
            }
            if (estado.equals("d")) {
                return 3;
            }
            if (estado.equals("e")) {
                return 4;
            }
            if (estado.equals("f")) {
                return 5;
            }
            if (estado.equals("g")) {
                return 6;
            }
            if (estado.equals("h")) {
                return 7;
            }
        }
        return -1;
    }

    //------------------------------------------------------------
    //------------------------------------------------------------    
    public int[][] buildFrecuenciaTransicionesControl(ArrayList<ResolutorSolucion> pruebas) {
        frecuenciaTransicionesControl = new int[13][13];

        for (int pos = 0; pos < pruebas.size(); pos++) {
            String[] cadena = pruebas.get(pos).getTransicionesControl();
            for (int i = 0; i < (cadena.length - 1); i++) {
                int pos1 = this.getPositionControl(cadena[i]);
                int pos2 = this.getPositionControl(cadena[i + 1]);
                if (pos1 != -1 && pos2 != -1) {
                    frecuenciaTransicionesControl[pos1][pos2]++;
                }
            }
        }
        return frecuenciaTransicionesControl;
    }

    //------------------------------------------------------------
    //------------------------------------------------------------    
    public int[][] buildFrecuenciaTransicionesCovariacion(ArrayList<ResolutorSolucion> pruebas) {
        frecuenciaTransicionesCovariacion = new int[8][8];

        for (int pos = 0; pos < pruebas.size(); pos++) {
            String[] cadena = pruebas.get(pos).getTransicionesCovariacion();
            for (int i = 0; i < (cadena.length - 1); i++) {
                int pos1 = this.getPositionCovariacion(cadena[i]);
                int pos2 = this.getPositionCovariacion(cadena[i + 1]);
                if (pos1 != -1 && pos2 != -1) {
                    frecuenciaTransicionesCovariacion[pos1][pos2]++;
                }
            }
        }
        return frecuenciaTransicionesCovariacion;
    }

    //------------------------------------------------------------
    //------------------------------------------------------------    
    public CategoriasTrayectorias computeNumberOfTrayectoriasControl(ArrayList<ResolutorSolucion> pruebas) {
        //
        CategoriasTrayectorias categorias = new CategoriasTrayectorias();
        int longitud;

        for (int pos = 0; pos < pruebas.size(); pos++) {
            //
            ATrayectoria trayectoria = pruebas.get(pos).getTrayectoriaControl();
            if (trayectoria != null) {
                //
                for (int posK = 0; posK < categorias.control.size(); posK++) {
                    if (categorias.control.get(posK).getNombre().equals(trayectoria.getNombre())) {
                        longitud = categorias.control.get(posK).getLongitud();
                        categorias.control.get(posK).setLongitud(longitud + 1);
                    }
                }
            } else {
                int tamano = categorias.control.size();
                longitud = categorias.control.get(tamano - 1).getLongitud();
                categorias.control.get(tamano - 1).setLongitud(longitud + 1);
            }
        }

        return categorias;
    }

    //------------------------------------------------------------
    //------------------------------------------------------------    
    public CategoriasTrayectorias computeNumberOfTrayectoriasCovariacion(ArrayList<ResolutorSolucion> pruebas) {
        //
        CategoriasTrayectorias categorias = new CategoriasTrayectorias();
        int longitud;

        for (int pos = 0; pos < pruebas.size(); pos++) {
            //
            ATrayectoria trayectoria = pruebas.get(pos).getTrayectoriaCovariacion();
            if (trayectoria != null) {
                //
                for (int posK = 0; posK < categorias.covariacion.size(); posK++) {
                    if (categorias.covariacion.get(posK).getNombre().equals(trayectoria.getNombre())) {
                        longitud = categorias.covariacion.get(posK).getLongitud();
                        categorias.covariacion.get(posK).setLongitud(longitud + 1);
                    }
                }
            } else {
                int tamano = categorias.covariacion.size();
                longitud = categorias.covariacion.get(tamano - 1).getLongitud();
                categorias.covariacion.get(tamano - 1).setLongitud(longitud + 1);
            }
        }

        return categorias;
    }

    // tablas de frecuencias
    public int[][] frecuenciaTransicionesControl;
    public int[][] frecuenciaTransicionesCovariacion;
}
