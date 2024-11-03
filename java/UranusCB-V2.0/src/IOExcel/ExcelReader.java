/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExcel;

import Uranus.Problema;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author takina
 */
public class ExcelReader {

    //--------------------------------------
    public ExcelReader() {
    }

    //--------------------------------------------------------------------------------------------------------------------
    public void readExcelToArray(File excelFile, Problema movimiento) throws IOException, InvalidFormatException {
        Workbook workbook = null;
        InputStream excelStream = null;
        ArrayList<String[]> arrayIntentos = null;
        String[] arrayHeaders = null;

        try {
            excelStream = new FileInputStream(excelFile);
            // Creating a Workbook from an Excel file (.xls or .xlsx)
            workbook = WorkbookFactory.create(excelStream);

            // Retrieving the number of sheets in the Workbook
            System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

            //   ==================================================================
            //   Iterating over all the rows and columns in a Sheet (Multiple ways)
            //   ==================================================================
            // Create a DataFormatter to format and get each cell's value as String
            DataFormatter dataFormatter = new DataFormatter();

            //   ==================================================================
            // Getting the Sheet at index zero                
            //   ==================================================================
            Sheet sheetHeader = workbook.getSheetAt(0);
            for (Row row : sheetHeader) {
                arrayHeaders = new String[row.getLastCellNum()];

                for (Cell cell : row) {
                    String cellValue = dataFormatter.formatCellValue(cell);

                    arrayHeaders[cell.getColumnIndex()] = cellValue;
                }
            }

            //   ==================================================================
            //   Iterating over all the sheets in the workbook (Multiple ways)
            //   ==================================================================
            for (int i = 1; i < workbook.getNumberOfSheets(); i++) {
                //   ==============================================================
                // Getting the Sheet at index one
                //   ==============================================================
                arrayIntentos = new ArrayList<>();
                
                Sheet sheetTries = workbook.getSheetAt(i);
                for (Row row : sheetTries) {

                    String[] datos = new String[row.getLastCellNum()];

                    for (Cell cell : row) {
                        String cellValue = dataFormatter.formatCellValue(cell);

                        datos[cell.getColumnIndex()] = cellValue;
                    }

                    arrayIntentos.add(datos);
                }

                //--------------------------------------------------------------
                //--------------------------------------------------------------
                // Adiciona los datos leidos del header y los intentos
                //--------------------------------------------------------------
                if (arrayIntentos.size() >= movimiento.MIN_INTENTOS) {
                    movimiento.addResolutorSolucion(arrayHeaders, arrayIntentos);
                } else {
                    movimiento.addResolutorSolucionNOvalido(arrayHeaders, arrayIntentos);
                }
            }
            //--------------------------------------------------------------

        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("The file not exists (No se encontró el archivo): " + fileNotFoundException);
        } catch (IOException ex) {
            System.out.println("Error in file procesing (Error al procesar el archivo): " + ex);
        } finally {
            try {
                // Closing the workbook
                workbook.close();
            } catch (IOException ex) {
                System.out.println("Error in file processing after close it (Error al procesar el archivo después de cerrarlo): " + ex);
            }
        }
    }

    //---------------------------------------------------------------------------------------------------
    public static String[] columns = {"Facultad", "Cedula", "Nombre", "Edad", "Sexo", "Semestre",
        "Intento1", "Intento2", "Intento3", "Intento4", "Intento5",
        "Intento6", "Intento7", "Intento8", "Intento9", "Intento10",
        "Intento11", "Intento12", "Intento13", "Intento14", "Intento15"};

}
