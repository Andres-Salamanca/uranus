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
public class AnalisisControl {

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

    private int getLongitudSegmento(String[] transiciones, int postInicial, HashSet<String> initialSet) {
        int longitud = 1;
        while ((postInicial + longitud) < transiciones.length) {
            if (initialSet.contains(transiciones[postInicial + longitud])) {
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
    // firstValue and secondValue son usadas para validar la alternancia
    //--------------------------------------------------------------------------------------
    public int firstValue(String[] transiciones, int longitud, int post, HashSet<String> firstSet, HashSet<String> secondSet) {
        if (post < (transiciones.length - 1)) {
            // evalua si la siguiente posicion no cambia, se detiene
            if (secondSet.contains(transiciones[post + 1])) {
                longitud++;
                return secondValue(transiciones, longitud, post + 1, firstSet, secondSet);
            }
        }
        return longitud;
    }

    public int secondValue(String[] transiciones, int longitud, int post, HashSet<String> firstSet, HashSet<String> secondSet) {
        if (post < transiciones.length - 1) {
            if (firstSet.contains(transiciones[post + 1])) {
                longitud++;
                return firstValue(transiciones, longitud, post + 1, firstSet, secondSet);
            }
        }
        return longitud;
    }

    private int getLongitudSegmentoAlternancia(String[] transiciones, int posInicial, HashSet<String> firstSet, HashSet<String> secondSet) {
        int longitud = 1;

        if (firstSet.contains(transiciones[posInicial])) {
            longitud = firstValue(transiciones, longitud, posInicial, firstSet, secondSet); // inicia con "c", "f");
        } else {
            longitud = secondValue(transiciones, longitud, posInicial, firstSet, secondSet); //inicia con "g", "h");
        }

        return longitud;
    }

    //--------------------------------------------------------------------------------------
    // Obtiene la longitud del trayecto
    //--------------------------------------------------------------------------------------
    private int getLongitudTrayecto(ArrayList<ASegmento> segmentos, int postInicial, HashSet<String> conjunto) {
        int longitud = 1;
        while ((postInicial + longitud) < segmentos.size()) {
            if (conjunto.contains(segmentos.get(postInicial + longitud).getNombre())) {
                longitud++;  // acumula el numero de segmentos
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

        float antVelocidad = 0;
        float antTiempo = 0;
        float antCercania = 0;
        int numIntentos = 0;

        for (Intento next : intentos) {
            float velocidad = next.getX();
            float tiempo = next.getY();
            float cercania = next.getZ();

            if (antVelocidad == 0 && antTiempo == 0) {
                // Primer dato a procesar
                antCercania = cercania;
                antVelocidad = velocidad;
                antTiempo = tiempo;
            } else {
                // mira las variaciones
                float magnitudCambio1 = Math.abs(velocidad - antVelocidad);
                float magnitudCambio2 = Math.abs(tiempo - antTiempo);

                if ((velocidad > antVelocidad)) {
                    if ((magnitudCambio1 <= 2) && ((magnitudCambio2 <= 2))) {
                        if (tiempo > antTiempo) {
                            valIntento = "j";
                        } else if (tiempo < antTiempo) {
                            valIntento = "k";
                        } else {
                            valIntento = "c";
                        }
                    } else if (tiempo > antTiempo) {
                        valIntento = "a";
                    } else if (tiempo < antTiempo) {
                        valIntento = "b";
                    } else {
                        valIntento = "c";
                    }
                } else if ((velocidad < antVelocidad)) {
                    if ((magnitudCambio1 <= 2) && ((magnitudCambio2 <= 2))) {
                        if (tiempo > antTiempo) {
                            valIntento = "l";
                        } else if (tiempo < antTiempo) {
                            valIntento = "m";
                        } else {
                            valIntento = "f";
                        }
                    } else if (tiempo > antTiempo) {
                        valIntento = "d";
                    } else if (tiempo < antTiempo) {
                        valIntento = "e";
                    } else {
                        valIntento = "f";
                    }
                } else if ((velocidad == antVelocidad)) {
                    if (tiempo > antTiempo) {
                        valIntento = "g";
                    } else if (tiempo < antTiempo) {
                        valIntento = "h";
                    } else {
                        valIntento = "i";
                    }
                }

                antCercania = cercania;
                antVelocidad = velocidad;
                antTiempo = tiempo;

                //----------------------------------------------------------
                // Valida el caso de no cambio y elimina dicha transición
                //----------------------------------------------------------
                if (valIntento.equals("i")) {
                    //System.out.println("No hay cambio de valores en la posición: " + numIntentos);
                    //----------------------------------------------------------
                    // Reducir el tamano de las transiciones
                    //----------------------------------------------------------
                    String[] transiciones1 = new String[transiciones.length - 1];
                    for (int i = 0; i < numIntentos; i++) {
                        transiciones1[i] = transiciones[i];
                    }
                    transiciones = transiciones1;
                } else {
                    // Si no hay cambio, no incluir la transicion
                    transiciones[numIntentos++] = valIntento;
                }
            }
        }
        return transiciones;
    }

    //--------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------
    public ArrayList<ASegmento> getSegmentos(String[] transiciones) {
        ArrayList<ASegmento> segmentos = new ArrayList<>();

        HashSet<String> conjunto1 = new HashSet<>();
        conjunto1.add("c");
        conjunto1.add("f");
        HashSet<String> conjunto2 = new HashSet<>();
        conjunto2.add("g");
        conjunto2.add("h");
        HashSet<String> conjunto3 = new HashSet<>();
        conjunto3.add("j");
        conjunto3.add("k");
        conjunto3.add("l");
        conjunto3.add("m");

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
                    case "e": {
                        longitud = getLongitudSegmento(transiciones, posInicial, "e");
                        ASegmento segmento = new ASegmento("sncd", longitud);
                        segmentos.add(segmento);
                        break;
                    }
                    case "b":
                    case "d": {
                        longitud = getLongitudSegmentoAlternancia(transiciones, posInicial, "b", "d");
                        if (longitud >= 2) {
                            ASegmento segmento = new ASegmento("snco", longitud);
                            segmentos.add(segmento);
                        } else {
                            if (transicion.equals("b")) {
                                longitud = getLongitudSegmento(transiciones, posInicial, "b");
                            } else {
                                longitud = getLongitudSegmento(transiciones, posInicial, "d");
                            }
                            ASegmento segmento = new ASegmento("snca", longitud);
                            segmentos.add(segmento);
                        }
                        break;
                    }
                    case "c":
                    case "g":
                    case "h":
                    case "f": {
                        longitud = getLongitudSegmentoAlternancia(transiciones, posInicial, conjunto1, conjunto2);

                        if (longitud >= 2) {
                            ASegmento segmento = new ASegmento("sco", longitud);
                            segmentos.add(segmento);
                        } else {
                            switch (transicion) {
                                case "c": {
                                    longitud = getLongitudSegmento(transiciones, posInicial, "c");
                                    ASegmento segmento = new ASegmento("scsc", longitud);
                                    segmentos.add(segmento);
                                    break;
                                }
                                case "g": {
                                    longitud = getLongitudSegmento(transiciones, posInicial, "g");
                                    ASegmento segmento = new ASegmento("scsc", longitud);
                                    segmentos.add(segmento);
                                    break;
                                }
                                case "h": {
                                    longitud = getLongitudSegmento(transiciones, posInicial, "h");
                                    ASegmento segmento = new ASegmento("scsd", longitud);
                                    segmentos.add(segmento);
                                    break;
                                }
                                case "f": {
                                    longitud = getLongitudSegmento(transiciones, posInicial, "f");
                                    ASegmento segmento = new ASegmento("scsd", longitud);
                                    segmentos.add(segmento);
                                    break;
                                }
                            }
                        }
                        break;
                    }
                    case "j":
                    case "k":
                    case "l":
                    case "m": {
                        longitud = getLongitudSegmento(transiciones, posInicial, conjunto3);
                        ASegmento segmento = new ASegmento("scc", longitud);
                        segmentos.add(segmento);
                        break;
                    }
                    case "i": {
                        //------------------------------------------------------------
                        // Este caso no se presenta porque se eliminó de la lista
                        // la transición de tipo i
                        //------------------------------------------------------------
                        longitud = getLongitudSegmento(transiciones, posInicial, "i");
                        ASegmento segmento = new ASegmento("F", longitud);
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
        conjunto1.add("snca");
        conjunto1.add("snco");

        HashSet<String> conjunto2 = new HashSet<>();
        conjunto2.add("scsc");
        conjunto2.add("scsd");
        conjunto2.add("scc");
        conjunto2.add("sco");

        int posInicial = 0;
        int longitud = 1;
        while (posInicial < segmentos.size()) {
            ASegmento segmento = segmentos.get(posInicial);

            // validar para crear los trayectos
            if (conjunto1.contains(segmento.getNombre())) {
                longitud = getLongitudTrayecto(segmentos, posInicial, conjunto1);
                ATrayecto trayecto = new ATrayecto("tnc", longitud);
                trayectos.add(trayecto);
            } else if (conjunto2.contains(segmento.getNombre())) {
                longitud = getLongitudTrayecto(segmentos, posInicial, conjunto2);
                ATrayecto trayecto = new ATrayecto("tc", longitud);
                trayectos.add(trayecto);
            } else {
                System.out.println("Tipo de segmento no definido para calcular el trayecto : " + segmento.getNombre());
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
                trayectoria = new ATrayectoria("SIN CONTROL", trayectos.size());
            } else {
                trayectoria = new ATrayectoria("CONTROL CONSOLIDADO", trayectos.size());
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
                trayectoria = new ATrayectoria("CONTROL NO CONSOLIDADO", trayectos.size());        //****
            } else if (trayecto1.getNombre().equals("tc") && trayecto2.getNombre().equals("tnc")) {
                trayectoria = new ATrayectoria("DESMEJORANTE", trayectos.size());                  //****
            } else if (trayecto1.getNombre().equals("tnc") && trayecto2.getNombre().equals("tc")) {
                trayectoria = new ATrayectoria("MEJORANTE CON INTERMITENCIA", trayectos.size());   //****
            } else if (trayecto1.getNombre().equals("tnc") && trayecto2.getNombre().equals("tnc")) {
                trayectoria = new ATrayectoria("SIN CONTROL", trayectos.size());                   //****
            }
        }

        return trayectoria;
    }
}