/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExcel;

import Uranus.ASegmento;
import Uranus.ATrayecto;
import Uranus.ATrayectoria;
import Uranus.CategoriasTrayectorias;
import Uranus.Intento;
import Uranus.Problema;
import Uranus.ResolutorSolucion;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author takina
 */
public class ExcelWriterControl {

    //--------------------------------------
    public ExcelWriterControl() {
    }

    //-----------------------------------------------------------------------
    public void writeResolutorIntentosExcelFile(Problema movimiento, String fileName ) throws IOException {
        // Create a Workbook
        XSSFWorkbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

        // Create a Sheet
        XSSFSheet sheet = workbook.createSheet("Tries");

        Map<String, CellStyle> styles = StyleDefinitions.createStyles(workbook);

        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Creating cells
        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(styles.get("header"));
        }

        // Create Other rows and cells with employees data
        int rowNum = 1;

        boolean indice = true;
        String estiloInt, estiloFloat;

        for (ResolutorSolucion resolutorsolucion: movimiento.pruebas)
        {
            //----------------------------------------------------------------
            // Define el estilo a ser utilizado
            //----------------------------------------------------------------
            if (indice){
                estiloInt   = "intentoIntTipo1";
                estiloFloat = "intentoFloatTipo1";
            }
            else{
                estiloInt   = "intentoIntTipo2";
                estiloFloat = "intentoFloatTipo2";
            }
            indice = !indice;

            //----------------------------------------------------------------
            //crea la nueva fila de datos
            //----------------------------------------------------------------
            Row row1 = sheet.createRow(rowNum++);
            Row row2 = sheet.createRow(rowNum++);
            Row row3 = sheet.createRow(rowNum++);

            //----------------------------------------------------------------
            // Asigna los valores del resolutor
            //----------------------------------------------------------------
            Cell cell;
            cell = row1.createCell(0);
            cell.setCellValue(resolutorsolucion.resolutor.getId_Facultad());
            cell.setCellStyle(styles.get(estiloInt));
            cell = row1.createCell(1);
            cell.setCellValue(resolutorsolucion.resolutor.getCedula());
            cell.setCellStyle(styles.get(estiloInt));
            cell = row1.createCell(2);
            cell.setCellValue(resolutorsolucion.resolutor.getNombres());
            cell.setCellStyle(styles.get(estiloInt));
            cell = row1.createCell(3);
            cell.setCellValue(resolutorsolucion.resolutor.getEdad());
            cell.setCellStyle(styles.get(estiloInt));
            cell = row1.createCell(4);
            cell.setCellValue(resolutorsolucion.resolutor.getSexo());
            cell.setCellStyle(styles.get(estiloInt));
            cell = row1.createCell(5);
            cell.setCellValue(resolutorsolucion.resolutor.getSemestre());
            cell.setCellStyle(styles.get(estiloInt));

            //----------------------------------------------------------------
            // Obtiene y asigna los valores de los intentos
            //----------------------------------------------------------------
            ArrayList<Intento> intentos = resolutorsolucion.getIntentos();
            int k = 6;

            for (Intento intento: intentos)
            {
                Cell cell1 = row1.createCell(k);
                cell1.setCellValue(intento.getX());
                cell1.setCellStyle(styles.get(estiloInt));

                Cell cell2 = row2.createCell(k);
                cell2.setCellValue(intento.getY());
                cell2.setCellStyle(styles.get(estiloInt));

                Cell cell3 = row3.createCell(k);
                cell3.setCellValue(intento.getZ());
                cell3.setCellStyle(styles.get(estiloFloat));

                k++;
            }

        }

        //----------------------------------------------------------------
        // Resize all columns to fit the content size
        //----------------------------------------------------------------
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        //----------------------------------------------------------------
        // Write the output to a file
        //----------------------------------------------------------------
        FileOutputStream fileOut = new FileOutputStream(fileName);
        workbook.write(fileOut);
        fileOut.close();

        //----------------------------------------------------------------
        // Closing the workbook
        //----------------------------------------------------------------
        workbook.close();
    }


    //------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------
    public void writeAllControlExcelFile(Problema movimiento, String fileName ) throws IOException {
        // Create a Workbook
        XSSFWorkbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

        // Create a Sheet
        XSSFSheet sheet = workbook.createSheet("AnalisisControl");

        Map<String, CellStyle> styles = StyleDefinitions.createStyles(workbook);

        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Creating cells
        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(styles.get("header"));
        }

        // Create Other rows and cells with employees data
        int rowNum = 1;

        boolean indice = true;
        String estiloInt, estiloFloat;

        for (ResolutorSolucion resolutorsolucion: movimiento.pruebas)
        {
            //----------------------------------------------------------------
            // Define el estilo a ser utilizado
            //----------------------------------------------------------------
            if (indice){
                estiloInt   = "intentoIntTipo1";
                estiloFloat = "intentoFloatTipo1";
            }
            else{
                estiloInt   = "intentoIntTipo2";
                estiloFloat = "intentoFloatTipo2";
            }
            indice = !indice;

            //crea las nuevas filas de datos
            Row row1 = sheet.createRow(rowNum++);
            Row row2 = sheet.createRow(rowNum++);
            Row row3 = sheet.createRow(rowNum++);
            Row row4 = sheet.createRow(rowNum++);
            Row row5 = sheet.createRow(rowNum++);
            Row row6 = sheet.createRow(rowNum++);
            Row row7 = sheet.createRow(rowNum++);
            Row row8 = sheet.createRow(rowNum++);
            Row row9 = sheet.createRow(rowNum++);
            Row row10 = sheet.createRow(rowNum++);

            //----------------------------------------------------------------
            // Asigna los valores del resolutor
            //----------------------------------------------------------------
            Cell cell;
            cell = row1.createCell(0);
            cell.setCellValue(resolutorsolucion.resolutor.getId_Facultad());
            cell.setCellStyle(styles.get(estiloInt));
            cell = row1.createCell(1);
            cell.setCellValue(resolutorsolucion.resolutor.getCedula());
            cell.setCellStyle(styles.get(estiloInt));
            cell = row1.createCell(2);
            cell.setCellValue(resolutorsolucion.resolutor.getNombres());
            cell.setCellStyle(styles.get(estiloInt));
            cell = row1.createCell(3);
            cell.setCellValue(resolutorsolucion.resolutor.getEdad());
            cell.setCellStyle(styles.get(estiloInt));
            cell = row1.createCell(4);
            cell.setCellValue(resolutorsolucion.resolutor.getSexo());
            cell.setCellStyle(styles.get(estiloInt));
            cell = row1.createCell(5);
            cell.setCellValue(resolutorsolucion.resolutor.getSemestre());
            cell.setCellStyle(styles.get(estiloInt));

            ArrayList<Intento> intentos = resolutorsolucion.getIntentos();
            int k = 6;
            for (Intento intento: intentos)
            {
                Cell cell1 = row1.createCell(k);
                cell1.setCellValue(intento.getX());
                cell1.setCellStyle(styles.get(estiloInt));

                Cell cell2 = row2.createCell(k);
                cell2.setCellValue(intento.getY());
                cell2.setCellStyle(styles.get(estiloInt));

                Cell cell3 = row3.createCell(k);
                cell3.setCellValue(intento.getZ());
                cell3.setCellStyle(styles.get(estiloFloat));

                k++;
            }

            // write Transiciones
            String[] transiciones = resolutorsolucion.getTransicionesControl();
            k = 6;
            for (String transicion: transiciones)
            {
                Cell cell1 = row4.createCell(k);
                cell1.setCellValue(transicion);
                cell1.setCellStyle(styles.get(estiloInt));

                k++;
            }

            // write Segmentos con longitud
            ArrayList<ASegmento> segmentos = resolutorsolucion.getSegmentosControl();
            k = 6;
            for (ASegmento segmento: segmentos)
            {
                Cell cell1 = row5.createCell(k);
                cell1.setCellValue(segmento.getLongitud());
                cell1.setCellStyle(styles.get(estiloInt));

                Cell cell2 = row6.createCell(k);
                cell2.setCellValue(segmento.getNombre());
                cell2.setCellStyle(styles.get(estiloInt));

                k++;
            }

            // write Trayectos con longitud
            ArrayList<ATrayecto> trayectos = resolutorsolucion.getTrayectosControl();
            k = 6;
            for (ATrayecto trayecto: trayectos)
            {
                Cell cell1 = row7.createCell(k);
                cell1.setCellValue(trayecto.getLongitud());
                cell1.setCellStyle(styles.get(estiloInt));

                Cell cell2 = row8.createCell(k);
                cell2.setCellValue(trayecto.getNombre());
                cell2.setCellStyle(styles.get(estiloInt));

                k++;
            }

            // write Trayectoria con longitud
            ATrayectoria trayectoria = resolutorsolucion.getTrayectoriaControl();
            if (trayectoria != null) {
                k = 6;
                Cell cell1 = row9.createCell(k);
                cell1.setCellValue(trayectoria.getLongitud());
                cell1.setCellStyle(styles.get(estiloInt));

                Cell cell2 = row10.createCell(k);
                cell2.setCellValue(trayectoria.getNombre());
                cell2.setCellStyle(styles.get(estiloInt));
            }
        }

        // Resize all columns to fit the content size
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream(fileName);
        workbook.write(fileOut);
        fileOut.close();

        // Closing the workbook
        workbook.close();

    }

    //------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------
    public void writeAllConsolidadoExcelFile(CategoriasTrayectorias categoriasControl,
                                             CategoriasTrayectorias categoriasCovariacion,
                                             int tamano, String fileName ) throws IOException {
        // Create a Workbook
        XSSFWorkbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

        // Create a Sheet
        XSSFSheet sheet = workbook.createSheet("ConsolidadoControl");

        Map<String, CellStyle> styles = StyleDefinitions.createStyles(workbook);

        //-----------------------------------------------------------------
        // Creating header cells
        //-----------------------------------------------------------------

        // Create a Row
        Row headerRow = sheet.createRow(0);

        Cell cell;
        cell = headerRow.createCell(0);
        cell.setCellValue("TRAYECTORIAS SEGUN LA DIMENSION CONTROL");
        cell.setCellStyle(styles.get("header"));
        cell = headerRow.createCell(1);
        cell.setCellValue("CANTIDAD");
        cell.setCellStyle(styles.get("header"));
        cell = headerRow.createCell(2);
        cell.setCellValue("PORCENTAJE");
        cell.setCellStyle(styles.get("header"));

        //-----------------------------------------------------------------
        // Write trayectorias
        //-----------------------------------------------------------------

        System.out.println("TRAYECTORIAS SEGUN LA DIMENSION CONTROL");

        int rowNum = 1;
        for (ATrayectoria trayectoria : categoriasControl.control) {
            //crea la nueva fila de datos
            Row row = sheet.createRow(rowNum++);

            cell = row.createCell(0);
            cell.setCellValue(trayectoria.getNombre());
            cell.setCellStyle(styles.get("intentoIntTipo1"));
            cell = row.createCell(1);
            cell.setCellValue(trayectoria.getLongitud());
            cell.setCellStyle(styles.get("intentoIntTipo1"));
            cell = row.createCell(2);
            cell.setCellValue(trayectoria.getLongitud()*1.0/tamano);
            cell.setCellStyle(styles.get("tipoFloatPorcentaje"));
        }

        //-----------------------------------------------------------------

        // Resize all columns to fit the content size
        for(int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }

//---------------------------------------------------------------------------
//---------------------------------------------------------------------------
        // Create a Sheet
        XSSFSheet sheet2 = workbook.createSheet("ConsolidadoCovariacion");

        //-----------------------------------------------------------------
        // Creating header cells
        //-----------------------------------------------------------------

        // Create a Row
        Row headerRow2 = sheet2.createRow(0);

        cell = headerRow2.createCell(0);
        cell.setCellValue("TRAYECTORIAS SEGUN LA DIMENSION COVARIACION");
        cell.setCellStyle(styles.get("header"));
        cell = headerRow2.createCell(1);
        cell.setCellValue("CANTIDAD");
        cell.setCellStyle(styles.get("header"));
        cell = headerRow2.createCell(2);
        cell.setCellValue("PORCENTAJE");
        cell.setCellStyle(styles.get("header"));

        //-----------------------------------------------------------------
        // Write trayectorias
        //-----------------------------------------------------------------

        System.out.println("TRAYECTORIAS SEGUN LA DIMENSION COVARIACION");

        rowNum = 1;
        for (ATrayectoria trayectoria : categoriasCovariacion.covariacion) {
            //crea la nueva fila de datos
            Row row = sheet2.createRow(rowNum++);

            cell = row.createCell(0);
            cell.setCellValue(trayectoria.getNombre());
            cell.setCellStyle(styles.get("intentoIntTipo1"));
            cell = row.createCell(1);
            cell.setCellValue(trayectoria.getLongitud());
            cell.setCellStyle(styles.get("intentoIntTipo1"));
            cell = row.createCell(2);
            cell.setCellValue(trayectoria.getLongitud()*1.0/tamano);
            cell.setCellStyle(styles.get("tipoFloatPorcentaje"));
        }
        //-----------------------------------------------------------------

        // Resize all columns to fit the content size
        for(int i = 0; i < 3; i++) {
            sheet2.autoSizeColumn(i);
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream(fileName);
        workbook.write(fileOut);
        fileOut.close();

        // Closing the workbook
        workbook.close();
    }

    //------------------------
    //-----------------------------------------------------------------------
    public void writeAllCovariacionExcelFile(Problema movimiento, String fileName ) throws IOException {
        // Create a Workbook
        XSSFWorkbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

        // CreationHelper helps us create instances for various things like DataFormat,
        //   Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way //
        CreationHelper createHelper = workbook.getCreationHelper();

        // Create a Sheet
        XSSFSheet sheet = workbook.createSheet("AnalisisCovariacion");

        Map<String, CellStyle> styles = StyleDefinitions.createStyles(workbook);

        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Creating cells
        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(styles.get("header"));
        }

        // Create Other rows and cells with employees data
        int rowNum = 1;

        boolean indice = true;
        String estiloInt, estiloFloat;

        for (ResolutorSolucion resolutorsolucion: movimiento.pruebas)
        {
            //----------------------------------------------------------------
            // Define el estilo a ser utilizado
            //----------------------------------------------------------------
            if (indice){
                estiloInt   = "intentoIntTipo1";
                estiloFloat = "intentoFloatTipo1";
            }
            else{
                estiloInt   = "intentoIntTipo2";
                estiloFloat = "intentoFloatTipo2";
            }
            indice = !indice;

            //crea la nueva fila de datos
            Row row1 = sheet.createRow(rowNum++);
            Row row2 = sheet.createRow(rowNum++);
            Row row3 = sheet.createRow(rowNum++);
            Row row4 = sheet.createRow(rowNum++);
            Row row5 = sheet.createRow(rowNum++);
            Row row6 = sheet.createRow(rowNum++);
            Row row7 = sheet.createRow(rowNum++);
            Row row8 = sheet.createRow(rowNum++);
            Row row9 = sheet.createRow(rowNum++);
            Row row10 = sheet.createRow(rowNum++);

            //----------------------------------------------------------------
            // Asigna los valores del resolutor
            //----------------------------------------------------------------
            Cell cell;
            cell = row1.createCell(0);
            cell.setCellValue(resolutorsolucion.resolutor.getId_Facultad());
            cell.setCellStyle(styles.get(estiloInt));
            cell = row1.createCell(1);
            cell.setCellValue(resolutorsolucion.resolutor.getCedula());
            cell.setCellStyle(styles.get(estiloInt));
            cell = row1.createCell(2);
            cell.setCellValue(resolutorsolucion.resolutor.getNombres());
            cell.setCellStyle(styles.get(estiloInt));
            cell = row1.createCell(3);
            cell.setCellValue(resolutorsolucion.resolutor.getEdad());
            cell.setCellStyle(styles.get(estiloInt));
            cell = row1.createCell(4);
            cell.setCellValue(resolutorsolucion.resolutor.getSexo());
            cell.setCellStyle(styles.get(estiloInt));
            cell = row1.createCell(5);
            cell.setCellValue(resolutorsolucion.resolutor.getSemestre());
            cell.setCellStyle(styles.get(estiloInt));

            ArrayList<Intento> intentos = resolutorsolucion.getIntentos();
            int k = 6;
            for (Intento intento: intentos)
            {
                Cell cell1 = row1.createCell(k);
                cell1.setCellValue(intento.getX());
                cell1.setCellStyle(styles.get(estiloInt));

                Cell cell2 = row2.createCell(k);
                cell2.setCellValue(intento.getY());
                cell2.setCellStyle(styles.get(estiloInt));

                Cell cell3 = row3.createCell(k);
                cell3.setCellValue(intento.getZ());
                cell3.setCellStyle(styles.get(estiloFloat));

                k++;
            }

            // write Transiciones
            String[] transiciones = resolutorsolucion.getTransicionesCovariacion();
            k = 6;
            for (String transicion: transiciones)
            {
                Cell cell1 = row4.createCell(k);
                cell1.setCellValue(transicion);
                cell1.setCellStyle(styles.get(estiloInt));

                k++;
            }

            // write Segmentos con longitud
            ArrayList<ASegmento> segmentos = resolutorsolucion.getSegmentosCovariacion();
            k = 6;
            for (ASegmento segmento: segmentos)
            {
                Cell cell1 = row5.createCell(k);
                cell1.setCellValue(segmento.getLongitud());
                cell1.setCellStyle(styles.get(estiloInt));

                Cell cell2 = row6.createCell(k);
                cell2.setCellValue(segmento.getNombre());
                cell2.setCellStyle(styles.get(estiloInt));

                k++;
            }

            // write Trayectos con longitud
            ArrayList<ATrayecto> trayectos = resolutorsolucion.getTrayectosCovariacion();
            k = 6;
            for (ATrayecto trayecto: trayectos)
            {
                Cell cell1 = row7.createCell(k);
                cell1.setCellValue(trayecto.getLongitud());
                cell1.setCellStyle(styles.get(estiloInt));

                Cell cell2 = row8.createCell(k);
                cell2.setCellValue(trayecto.getNombre());
                cell2.setCellStyle(styles.get(estiloInt));

                k++;
            }

            // write Trayectoria con longitud
            ATrayectoria trayectoria = resolutorsolucion.getTrayectoriaCovariacion();
            k = 6;
            Cell cell1 = row9.createCell(k);
            cell1.setCellValue(trayectoria.getLongitud());
            cell1.setCellStyle(styles.get(estiloInt));

            Cell cell2 = row10.createCell(k);
            cell2.setCellValue(trayectoria.getNombre());
            cell2.setCellStyle(styles.get(estiloInt));
        }

        // Resize all columns to fit the content size
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream(fileName);
        workbook.write(fileOut);
        fileOut.close();

        // Closing the workbook
        workbook.close();

    }

    //---------------------------------------------------------------------------------------------------
    public static String[] columns = {"Facultad", "Cedula", "Nombre", "Edad", "Sexo", "Semestre",
            "Intento1", "Intento2", "Intento3", "Intento4", "Intento5",
            "Intento6", "Intento7", "Intento8", "Intento9", "Intento10",
            "Intento11", "Intento12", "Intento13", "Intento14", "Intento15"};

}