/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExcel;

import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author takina
 */
public class StyleDefinitions {
    /**
     * cell styles used for formatting calendar sheets
     */
    public static Map<String, CellStyle> createStyles(Workbook wb){
        //--------------------------------------------------------
        // Define un mapa de estilos para la salida con formato
        //--------------------------------------------------------
        Map<String, CellStyle> styles = new HashMap<>();

        //------------------------------------------------------------------------------
        // CreationHelper helps us create instances for various things like DataFormat,
        // Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
        //------------------------------------------------------------------------------
        CreationHelper createHelper = wb.getCreationHelper();

        short borderColor = IndexedColors.GREY_50_PERCENT.getIndex();

        CellStyle style;

        // Create a Font and CellStyle for header cells
        Font titleFont = wb.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 10);
        titleFont.setColor(IndexedColors.BLUE.getIndex());
        style = wb.createCellStyle();
//        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(titleFont);
        styles.put("header", style);


/*        Font intentoIntTipo1Font = wb.createFont();
        intentoIntTipo1Font.setFontHeightInPoints((short)12);
        intentoIntTipo1Font.setColor(IndexedColors.WHITE.getIndex());
        intentoIntTipo1Font.setBold(true);
*/        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        style.setFont(intentoIntTipo1Font);
        styles.put("intentoIntTipo1", style);


/*        Font intentoIntTipo2Font = wb.createFont();
        intentoIntTipo2Font.setFontHeightInPoints((short)12);
        intentoIntTipo2Font.setColor(IndexedColors.WHITE.getIndex());
        intentoIntTipo2Font.setBold(true);
*/        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        style.setFont(intentoIntTipo2Font);
        styles.put("intentoIntTipo2", style);


/*        Font intentoFloatTipo1Font = wb.createFont();
        intentoFloatTipo1Font.setFontHeightInPoints((short)12);
        intentoFloatTipo1Font.setColor(IndexedColors.WHITE.getIndex());
        intentoFloatTipo1Font.setBold(true);
*/        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        style.setFont(intentoFloatTipo1Font);
        styles.put("intentoFloatTipo1", style);

/*        Font intentoFloatTipo2Font = wb.createFont();
        intentoFloatTipo2Font.setFontHeightInPoints((short)12);
        intentoFloatTipo2Font.setColor(IndexedColors.WHITE.getIndex());
        intentoFloatTipo2Font.setBold(true);
*/        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        style.setFont(intentoFloatTipo2Font);
        styles.put("intentoFloatTipo2", style);


/*        Font intentoFloatTipo2Font = wb.createFont();
        intentoFloatTipo2Font.setFontHeightInPoints((short)12);
        intentoFloatTipo2Font.setColor(IndexedColors.WHITE.getIndex());
        intentoFloatTipo2Font.setBold(true);
*/        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setDataFormat(createHelper.createDataFormat().getFormat("0.00%"));
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        style.setFont(intentoFloatTipo2Font);
        styles.put("tipoFloatPorcentaje", style);

//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------

        Font dayFont = wb.createFont();
        dayFont.setFontHeightInPoints((short)14);
        dayFont.setBold(true);
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(borderColor);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(borderColor);
        style.setFont(dayFont);
        styles.put("weekend_left", style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(borderColor);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(borderColor);
        styles.put("weekend_right", style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setBorderLeft(BorderStyle.THIN);
        style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setLeftBorderColor(borderColor);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(borderColor);
        style.setFont(dayFont);
        styles.put("workday_left", style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(borderColor);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(borderColor);
        styles.put("workday_right", style);

        style = wb.createCellStyle();
        style.setBorderLeft(BorderStyle.THIN);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(borderColor);
        styles.put("grey_left", style);

        style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(borderColor);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(borderColor);
        styles.put("grey_right", style);

        return styles;
    }

}