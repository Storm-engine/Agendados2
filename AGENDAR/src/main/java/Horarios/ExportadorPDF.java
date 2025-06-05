/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Horarios;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.swing.JTable;
/**
 *
 * @author Sergio David
 */


public class ExportadorPDF {

    public static void exportarJTableAPDF(JTable tabla, String nombreArchivo) {
        Document documento = new Document();

        try {
            PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));
            documento.open();

            PdfPTable pdfTabla = new PdfPTable(tabla.getColumnCount());

            // Añadir encabezados
            for (int i = 0; i < tabla.getColumnCount(); i++) {
                pdfTabla.addCell(new PdfPCell(new Phrase(tabla.getColumnName(i))));
            }

            // Añadir filas
            for (int fila = 0; fila < tabla.getRowCount(); fila++) {
                for (int col = 0; col < tabla.getColumnCount(); col++) {
                    Object valor = tabla.getValueAt(fila, col);
                    pdfTabla.addCell(new PdfPCell(new Phrase(valor != null ? valor.toString() : "")));
                }
            }

            documento.add(pdfTabla);
        } catch (DocumentException | FileNotFoundException e) {
        } finally {
            documento.close();
        }
    }
}

