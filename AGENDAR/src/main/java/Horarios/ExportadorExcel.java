package horarios;

import java.io.File;
import javax.swing.JTable;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.usermodel.*;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExportadorExcel {

    public static void exportarJTableAExcel(JTable tabla) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar como Excel");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos Excel (.xlsx)", "xlsx"));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            String rutaArchivo = archivoSeleccionado.getAbsolutePath();

            
            if (!rutaArchivo.toLowerCase().endsWith(".xlsx")) {
                rutaArchivo += ".xlsx";
            }

            
            Workbook libro = new XSSFWorkbook();
            Sheet hoja = libro.createSheet("Datos");

            
            CellStyle estiloEncabezado = libro.createCellStyle();
            Font fontEncabezado = libro.createFont();
            fontEncabezado.setBold(true);
            estiloEncabezado.setFont(fontEncabezado);

            
            Row filaEncabezado = hoja.createRow(0);
            for (int i = 0; i < tabla.getColumnCount(); i++) {
                Cell celda = filaEncabezado.createCell(i);
                celda.setCellValue(tabla.getColumnName(i));
                celda.setCellStyle(estiloEncabezado);
            }

            
            for (int f = 0; f < tabla.getRowCount(); f++) {
                Row fila = hoja.createRow(f + 1);
                for (int c = 0; c < tabla.getColumnCount(); c++) {
                    Object valor = tabla.getValueAt(f, c);
                    fila.createCell(c).setCellValue(valor != null ? valor.toString() : "");
                }
            }

            
            try (FileOutputStream archivo = new FileOutputStream(rutaArchivo)) {
                libro.write(archivo);
                libro.close();
                System.out.println("Archivo guardado en: " + rutaArchivo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}