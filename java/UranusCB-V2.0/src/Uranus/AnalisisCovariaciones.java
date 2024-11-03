/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Uranus;

import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author takina
 */
public class AnalisisCovariaciones {

    //--------------------------------------------------------------------------------------
    // Obtiene la longitud de una secuencia del mismo tipo
    //--------------------------------------------------------------------------------------
    private int getLongitudSegmento(String[] transiciones, int postInicial, String value) {
        int longitud = 1;
        while ((postInicial + longitud) < transiciones.length) {
            if (transiciones[postInicial + longitud] != null && transiciones[postInicial + longitud].equals(value)) {
                longitud++;
            } else {
                break;
            }
        }
        return longitud;
    }

    //--------------------------------------------------------------------------------------
    // firstValue and secondValue son usadas para validar la alternancia
    //--------------------------------------------------------------------------------------
    public int firstValue(String[] transiciones, int longitud, int post, String firstValue, String secondValue) {
        if (post < (transiciones.length - 1)) {
            // evalua si la siguiente posicion no cambia, se detiene
            if (transiciones[post + 1] != null && transiciones[post + 1].equals(secondValue)) {
                longitud++;
                return secondValue(transiciones, longitud, post + 1, firstValue, secondValue);
            }
        }
        return longitud;
    }

    public int secondValue(String[] transiciones, int longitud, int post, String firstValue, String secondValue) {
        if (post < transiciones.length - 1) {
            if (transiciones[post + 1] != null && transiciones[post + 1].equals(firstValue)) {
                longitud++;
                return firstValue(transiciones, longitud, post + 1, firstValue, secondValue);
            }
        }
        return longitud;
    }

    //--------------------------------------------------------------------------------------
    // Obtiene la longitud de una secuencia oscilante
    //--------------------------------------------------------------------------------------
    private int getLongitudSegmentoAlternancia(String[] transiciones, int posInicial, String firstValue, String secondValue) {
        int longitud = 1;
        if (transiciones[posInicial].equals(firstValue)) {
            longitud = firstValue(transiciones, longitud, posInicial, firstValue, secondValue); //"h", "g");
        } else {
            longitud = secondValue(transiciones, longitud, posInicial, firstValue, secondValue); //"g", "h");         
        }
        return longitud;

    }

    //--------------------------------------------------------------------------------------
    // Obtiene la longitud del trayecto
    //--------------------------------------------------------------------------------------
    private int getLongitudTrayecto(ArrayList<ASegmento> segmentos, int posInicial, HashSet<String> conjunto) {
        int longitud = 1;
        while ((posInicial + longitud) < segmentos.size()) {
            if (conjunto.contains(segmentos.get(posInicial + longitud).getNombre())) {
                longitud++;
            } else {
                break;
            }
        }
        return longitud;
    }

    //--------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------
    public String[] getTransiciones(ArrayList<Intento> intentos) {
        String[] transiciones = new String[intentos.size() - 1];
        String valIntento = "0";

        float antError = 0;
        float antVelocidad = 0;
        float antTiempo = 0;
        int numIntentos = 0;

        for (Intento next : intentos) {
            float velocidad = next.getX();
            float tiempo = next.getY();
            float error = next.getError();

            if (antVelocidad == 0 && antTiempo == 0) {
                // Primer dato a procesar
                antError = error;
                antVelocidad = velocidad;
                antTiempo = tiempo;
            } else {
                // mira las variaciones
                if (antError >= 0 && error >= 0) //mismo lado error positivo
                {
                    if (antError < error) {
                        valIntento = "a"; // aumenta el error
                    } else {
                        valIntento = "c"; // reduce el error
                    }
                } else if (antError < 0 && error < 0) //mismo lado error negativo
                {
                    if (antError > error) {
                        valIntento = "b"; // aumenta el error
                    } else {
                        valIntento = "d"; // reduce el error
                    }
                } else if (antError >= 0 && error < 0) //lados opuestos (positivo a negativo)
                {
                    if (Math.abs(antError) > Math.abs(error)) {
                        valIntento = "e"; // reduce el error
                    } else {
                        valIntento = "g"; // aumenta el error
                    }
                } else if (antError < 0 && error >= 0) //lados opuestos (negativo a positivo)
                {
                    if (Math.abs(antError) > Math.abs(error)) {
                        valIntento = "f"; // reduce el error
                    } else {
                        valIntento = "h"; // aumenta el error
                    }
                }

                antError = error;
                antVelocidad = velocidad;
                antTiempo = tiempo;

                transiciones[numIntentos++] = valIntento;
            }
        }
        return transiciones;
    }

    //--------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------
    public ArrayList<ASegmento> getSegmentos(String[] transiciones) {
        ArrayList<ASegmento> segmentos = new ArrayList<>();
        int posInicial = 0;
        int longitud = 1;
        while (posInicial < transiciones.length) {
            String transicion = transiciones[posInicial];

            if (transicion != null) {
                // validar para crear los segmentos
                
                switch (transicion) {
                    case "a": {
                        longitud = getLongitudSegmento(transiciones, posInicial, "a");
                        ASegmento segmento = new ASegmento("sncc", longitud);
                        segmentos.add(segmento);
                        break;
                    }
                    case "b": {
                        longitud = getLongitudSegmento(transiciones, posInicial, "b");
                        ASegmento segmento = new ASegmento("sncd", longitud);
                        segmentos.add(segmento);
                        break;
                    }
                    case "c": {
                        longitud = getLongitudSegmento(transiciones, posInicial, "c");
                        ASegmento segmento = new ASegmento("scc", longitud);
                        segmentos.add(segmento);
                        break;
                    }
                    case "d": {
                        longitud = getLongitudSegmento(transiciones, posInicial, "d");
                        ASegmento segmento = new ASegmento("scd", longitud);
                        segmentos.add(segmento);
                        break;
                    }
                    case "h":
                    case "g": {
                        longitud = getLongitudSegmentoAlternancia(transiciones, posInicial, "h", "g");
                        ASegmento segmento = new ASegmento("snco", longitud);
                        segmentos.add(segmento);
                        break;
                    }
                    case "e":
                    case "f": {
                        longitud = getLongitudSegmentoAlternancia(transiciones, posInicial, "e", "f");
                        ASegmento segmento = new ASegmento("sco", longitud);
                        segmentos.add(segmento);
                        break;
                    }
                    default:
                        break;
                }
                
            }

            posInicial = posInicial + (longitud - 1);
            posInicial++;
        }
        return segmentos;
    }

    //--------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------
    public ArrayList<ATrayecto> getTrayectos(ArrayList<ASegmento> segmentos) {
        ArrayList<ATrayecto> trayectos = new ArrayList<>();

        HashSet<String> conjunto1 = new HashSet<>();
        conjunto1.add("sncc");
        conjunto1.add("sncd");
        conjunto1.add("snco");

        HashSet<String> conjunto2 = new HashSet<>();
        conjunto2.add("scc");
        conjunto2.add("scd");
        conjunto2.add("sco");

        int posInicial = 0;
        int longitud = 1;
        while (posInicial < segmentos.size()) {
            ASegmento segmento = segmentos.get(posInicial);

            // validar para crear los trayectos
            switch (segmento.getNombre()) {
                case "sncc":
                case "sncd":
                case "snco": {
                    longitud = getLongitudTrayecto(segmentos, posInicial, conjunto1);
                    ATrayecto trayecto = new ATrayecto("tnc", longitud);
                    trayectos.add(trayecto);
                    break;
                }
                case "scc":
                case "scd":
                case "sco": {
                    longitud = getLongitudTrayecto(segmentos, posInicial, conjunto2);
                    ATrayecto trayecto = new ATrayecto("tc", longitud);
                    trayectos.add(trayecto);
                    break;
                }
                default:
                    break;
            }

            posInicial = posInicial + (longitud - 1);
            posInicial++;
        }
        return trayectos;
    }

    //-----------------------------------------------------------------------
    //------------------------------------------------------------------------
    public ATrayectoria getTrayectoria(ArrayList<ATrayecto> trayectos) {
        ATrayectoria trayectoria = null;

        if (trayectos.size() == 1) {
            ATrayecto trayecto = trayectos.get(0);
            if (trayecto.getNombre().equals("tnc")) {
                trayectoria = new ATrayectoria("SIN COVARIACION", trayectos.size());
            } else {
                trayectoria = new ATrayectoria("COVARIACION CONSOLIDADA", trayectos.size());
            }
        } else if (trayectos.size() == 2) {
            ATrayecto trayecto1 = trayectos.get(0);
            ATrayecto trayecto2 = trayectos.get(1);

            if (trayecto1.getNombre().equals("tnc") && trayecto2.getNombre().equals("tc")) {
                trayectoria = new ATrayectoria("MEJORANTE SIN INTERMITENCIA", trayectos.size());
            } else if (trayecto1.getNombre().equals("tc") && trayecto2.getNombre().equals("tnc")) {
                trayectoria = new ATrayectoria("DESMEJORANTE", trayectos.size());
            }
        } else if (trayectos.size() > 2) {
            ATrayecto trayecto1 = trayectos.get(0);
            ATrayecto trayecto2 = trayectos.get(trayectos.size() - 1);

            if (trayecto1.getNombre().equals("tc") && trayecto2.getNombre().equals("tc")) {
                trayectoria = new ATrayectoria("COVARIACION NO CONSOLIDADA", trayectos.size());    //****                
            } else if (trayecto1.getNombre().equals("tc") && trayecto2.getNombre().equals("tnc")) {
                trayectoria = new ATrayectoria("DESMEJORANTE", trayectos.size());                  //****                
            } else if (trayecto1.getNombre().equals("tnc") && trayecto2.getNombre().equals("tc")) {
                trayectoria = new ATrayectoria("MEJORANTE CON INTERMITENCIA", trayectos.size());   //****
            } else if (trayecto1.getNombre().equals("tnc") && trayecto2.getNombre().equals("tnc")) {
                trayectoria = new ATrayectoria("SIN COVARIACION", trayectos.size());               //****               
            }
        }

        return trayectoria;
    }
}
