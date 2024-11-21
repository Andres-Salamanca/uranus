/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Uranus;

import IOExcel.ExcelReader;
import IOExcel.ExcelWriterControl;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 *
 * @author takina
 */
public class Problema {

    //------------------------------------------------------------
    //------------------------------------------------------------
    public Problema(String descripcion) {
        this.descripcion = descripcion;
        pruebas = new ArrayList<ResolutorSolucion>();
        pruebasNOvalidas = new ArrayList<ResolutorSolucion>();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        outputfolder = sdf.format(timestamp);

        try {
            path = System.getProperty("user.dir");
            if (path.contains("dist"))
                path = path.replace(File.separator + "dist", "");
            String sDirectorio = path + File.separator + "data" + File.separator + "report";
        
            // returns pathnames for files and directory
            File f = new File(sDirectorio + File.separator + outputfolder);

            // create
            boolean bool = f.mkdir();

            // print
            System.out.print("Directory created? " + bool);
        } catch (Exception e) {
            // if any error occurs
            e.printStackTrace();
        }
    }

    //------------------------------------------------------------
    //------------------------------------------------------------
    public boolean loadInformation(String sDirectorio) {
        String sCarpeta = null;
        String sArchivo = null;

        ExcelReader readerExcelFiles = new ExcelReader();

        File f = new File(sDirectorio);

        if (f.exists()) // Directorio existe
        {
            File[] carpetas = f.listFiles();

            for (int x = 0; x < carpetas.length; x++) {
                sCarpeta = carpetas[x].getName();

                if (sCarpeta != null && !sCarpeta.contains(".gitignore")) {
                    sArchivo = sDirectorio + File.separator + sCarpeta + File.separator + "session_0.xlsx";
                    try {
                        // lee el archivo excel del resolutor y la almacena
                        readerExcelFiles.readExcelToArray(new File(sArchivo), this);

                    } catch (IOException ex) {
                        Logger.getLogger(ExcelReader.class.getName()).log(Level.SEVERE, null, ex);
                        return false;
                    } catch (InvalidFormatException ex) {
                        Logger.getLogger(ExcelReader.class.getName()).log(Level.SEVERE, null, ex);
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    //------------------------------------------------------------
    //------------------------------------------------------------
    public void writeResolutorIntentosExcelFile() {
        ExcelWriterControl writerExcelFile = new ExcelWriterControl();
        try {
            writerExcelFile.writeResolutorIntentosExcelFile(this,path + File.separator + "data" + File.separator + "report" + File.separator + outputfolder + File.separator + "Datos-Consolidados-Cambio-Cognitivo.xlsx");
        } catch (IOException ex) {
            Logger.getLogger(Problema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //------------------------------------------------------------
    //------------------------------------------------------------
    public void writeAllControlExcelFile() {
        ExcelWriterControl writerExcelFile = new ExcelWriterControl();
        try {
            writerExcelFile.writeAllControlExcelFile(this,path + File.separator + "data" + File.separator + "report" + File.separator +  outputfolder + File.separator + "AnalisisControl-Cambio-Cognitivo.xlsx");
        } catch (IOException ex) {
            Logger.getLogger(Problema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //------------------------------------------------------------
    //------------------------------------------------------------
    public void writeConsolidadoControlExcelFile() {
        CategoriasTrayectorias datosControl = (new MatrizTransiciones()).computeNumberOfTrayectoriasControl(this.pruebas);
        CategoriasTrayectorias datosCovariacion = (new MatrizTransiciones()).computeNumberOfTrayectoriasCovariacion(this.pruebas);

        ExcelWriterControl writerExcelFile = new ExcelWriterControl();
        try {
            writerExcelFile.writeAllConsolidadoExcelFile(datosControl, datosCovariacion, this.pruebas.size(),path + File.separator + "data" + File.separator + "report" + File.separator + outputfolder + File.separator + "ConsolidadoAnalisis-Cambio-Cognitivo.xlsx");
        } catch (IOException ex) {
            Logger.getLogger(Problema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //------------------------------------------------------------
    //------------------------------------------------------------
    public void writeAllCovariacionExcelFile() {
        ExcelWriterControl writerExcelFile = new ExcelWriterControl();
        try {
            writerExcelFile.writeAllCovariacionExcelFile(this,path + File.separator + "data" + File.separator + "report" + File.separator + outputfolder + File.separator + "AnalisisCovariacion-Cambio-Cognitivo.xlsx");
        } catch (IOException ex) {
            Logger.getLogger(Problema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //------------------------------------------------------------
    //------------------------------------------------------------
    public void addResolutorSolucion(String[] datosResolutor, ArrayList<String[]> sequence) {
        ResolutorSolucion rs = new ResolutorSolucion();
        rs.setResolutor(datosResolutor);
        rs.setIntentos(sequence);
        this.pruebas.add(rs);
    }

    //------------------------------------------------------------
    //------------------------------------------------------------
    public void addResolutorSolucionNOvalido(String[] datosResolutor, ArrayList<String[]> sequence) {
        ResolutorSolucion rs = new ResolutorSolucion();
        rs.setResolutor(datosResolutor);
        rs.setIntentos(sequence);
        this.pruebasNOvalidas.add(rs);
    }

    //------------------------------------------------------------
    //------------------------------------------------------------
    public void calcularSolucionControl() {
        for (int pos = 0; pos < pruebas.size(); pos++) {
            pruebas.get(pos).calcularControl();
        }
    }

    //------------------------------------------------------------
    //------------------------------------------------------------
    public void calcularSolucionCovariacion() {
        for (int pos = 0; pos < pruebas.size(); pos++) {
            pruebas.get(pos).calcularCovariacion();
        }
    }

    //------------------------------------------------------------
    //------------------------------------------------------------    
    public void printAllControl() {
        ImpresionDatos imprimir = new ImpresionDatos();
        for (int pos = 0; pos < pruebas.size(); pos++) {
            imprimir.printIntentos(pruebas.get(pos).getIntentos());
            imprimir.printTransiciones(pruebas.get(pos).getTransicionesControl());
            imprimir.printSegmentos(pruebas.get(pos).getSegmentosControl());
            imprimir.printTrayectos(pruebas.get(pos).getTrayectosControl());
            imprimir.printTrayectoria(pruebas.get(pos).getTrayectoriaControl());
        }
    }

    //------------------------------------------------------------
    //------------------------------------------------------------    
    public void printAllCovariacion() {
        ImpresionDatos imprimir = new ImpresionDatos();
        for (int pos = 0; pos < pruebas.size(); pos++) {
            imprimir.printIntentos(pruebas.get(pos).getIntentos());
            imprimir.printTransiciones(pruebas.get(pos).getTransicionesCovariacion());
            imprimir.printSegmentos(pruebas.get(pos).getSegmentosCovariacion());
            imprimir.printTrayectos(pruebas.get(pos).getTrayectosCovariacion());
            imprimir.printTrayectoria(pruebas.get(pos).getTrayectoriaCovariacion());
        }
    }

    //------------------------------------------------------------
    //------------------------------------------------------------    
    public void printIntentos() {
        ImpresionDatos imprimir = new ImpresionDatos();
        for (int pos = 0; pos < pruebas.size(); pos++) {
            imprimir.printIntentos(pruebas.get(pos).getIntentos());
        }
    }

    //------------------------------------------------------------
    //------------------------------------------------------------
    public void printTransicionesControl() {
        ImpresionDatos imprimir = new ImpresionDatos();
        for (int pos = 0; pos < pruebas.size(); pos++) {
            imprimir.printTransiciones(pruebas.get(pos).getTransicionesControl());
        }
    }

    //------------------------------------------------------------
    //------------------------------------------------------------
    public void printTransicionesCovariacion() {
        ImpresionDatos imprimir = new ImpresionDatos();
        for (int pos = 0; pos < pruebas.size(); pos++) {
            imprimir.printTransiciones(pruebas.get(pos).getTransicionesCovariacion());
        }
    }

    //------------------------------------------------------------
    //------------------------------------------------------------
    public void printSegmentosControl() {
        ImpresionDatos imprimir = new ImpresionDatos();
        for (int pos = 0; pos < pruebas.size(); pos++) {
            imprimir.printSegmentos(pruebas.get(pos).getSegmentosControl());
        }
    }

    //------------------------------------------------------------
    //------------------------------------------------------------
    public void printSegmentosCovariacion() {
        ImpresionDatos imprimir = new ImpresionDatos();
        for (int pos = 0; pos < pruebas.size(); pos++) {
            imprimir.printSegmentos(pruebas.get(pos).getSegmentosCovariacion());
        }
    }

    //------------------------------------------------------------
    //------------------------------------------------------------
    public void printTrayectosControl() {
        ImpresionDatos imprimir = new ImpresionDatos();
        for (int pos = 0; pos < pruebas.size(); pos++) {
            imprimir.printTrayectos(pruebas.get(pos).getTrayectosControl());
        }
    }

    //------------------------------------------------------------
    //------------------------------------------------------------
    public void printTrayectosCovariacion() {
        ImpresionDatos imprimir = new ImpresionDatos();
        for (int pos = 0; pos < pruebas.size(); pos++) {
            imprimir.printTrayectos(pruebas.get(pos).getTrayectosCovariacion());
        }
    }

    //------------------------------------------------------------
    //------------------------------------------------------------
    public void printFrecuenciaTransicionesControl() {
        ImpresionDatos imprimir = new ImpresionDatos();
        imprimir.printFrecuenciaTransiciones(new MatrizTransiciones().buildFrecuenciaTransicionesControl(pruebas), 13);
    }

    //------------------------------------------------------------
    //------------------------------------------------------------
    public void printFrecuenciaTransicionesCovariacion() {
        ImpresionDatos imprimir = new ImpresionDatos();
        imprimir.printFrecuenciaTransiciones(new MatrizTransiciones().buildFrecuenciaTransicionesCovariacion(pruebas), 8);
    }

    //------------------------------------------------------------
    //------------------------------------------------------------
    public void imprimirTrayectoriasControl() {
        ImpresionDatos imprimir = new ImpresionDatos();
        imprimir.printAcumuladoTrayectoriasControl(new MatrizTransiciones().computeNumberOfTrayectoriasControl(pruebas), pruebas.size());
    }

    //------------------------------------------------------------
    //------------------------------------------------------------
    public void imprimirTrayectoriasCovariacion() {
        ImpresionDatos imprimir = new ImpresionDatos();
        imprimir.printAcumuladoTrayectoriasCovariacion(new MatrizTransiciones().computeNumberOfTrayectoriasCovariacion(pruebas), pruebas.size());
    }

    //-----------------------------------------------------------------------  
    // atributos de la clase
    //----------------------------------------------------------------------- 
    public final int MIN_INTENTOS = 3;

    public String path;
    public String descripcion;
    public ArrayList<ResolutorSolucion> pruebas;
    public String outputfolder;
    public ArrayList<ResolutorSolucion> pruebasNOvalidas;
}
