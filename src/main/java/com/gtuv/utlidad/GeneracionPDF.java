package com.gtuv.utlidad;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class GeneracionPDF {

    private GeneracionPDF() {
    }

    public static void generarReporteGeneral(File archivo, 
                                             String tituloReporte, 
                                             Map<String, String> infoEncabezado,
                                             List<String> columnasRiesgo, List<List<String>> datosRiesgo,
                                             List<String> columnasProblemas, List<List<String>> datosProblemas,
                                             String comentarios) throws DocumentException, IOException {
        
        Document documento = new Document();
        PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(archivo));
        
        Membrete eventoMembrete = new Membrete();
        writer.setPageEvent(eventoMembrete);

        documento.open();

        crearPortada(documento, tituloReporte);
        
        Font fuenteInfo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.DARK_GRAY);
        for (Map.Entry<String, String> entry : infoEncabezado.entrySet()) {
            Paragraph info = new Paragraph(entry.getKey() + ": " + entry.getValue(), fuenteInfo);
            info.setAlignment(Element.ALIGN_LEFT);
            documento.add(info);
        }
        documento.add(new Paragraph("\n"));

        agregarSeccionTabla(documento, "Tutorados en Riesgo", columnasRiesgo, datosRiesgo);

        agregarSeccionTabla(documento, "Problemáticas Académicas Reportadas", columnasProblemas, datosProblemas);

        documento.add(new Paragraph("Comentarios Generales:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.DARK_GRAY)));
        documento.add(new Paragraph(" "));
        
        Font fuenteTexto = FontFactory.getFont(FontFactory.HELVETICA, 11);
        if (comentarios == null || comentarios.isEmpty()) {
            documento.add(new Paragraph("Sin comentarios registrados.", fuenteTexto));
        } else {
            documento.add(new Paragraph(comentarios, fuenteTexto));
        }

        documento.close();
    }

    private static void agregarSeccionTabla(Document documento, String titulo, List<String> encabezados, List<List<String>> datos) throws DocumentException {
        Paragraph tituloSeccion = new Paragraph(titulo, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.DARK_GRAY));
        tituloSeccion.setSpacingBefore(10);
        tituloSeccion.setSpacingAfter(10);
        documento.add(tituloSeccion);

        if (datos == null || datos.isEmpty()) {
            documento.add(new Paragraph("No hay registros para mostrar en esta sección.", FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10)));
            documento.add(new Paragraph("\n"));
            return;
        }

        PdfPTable tabla = new PdfPTable(encabezados.size());
        tabla.setWidthPercentage(100);

        Font fuenteCabecera = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.WHITE);
        Font fuenteDatos = FontFactory.getFont(FontFactory.HELVETICA, 9, BaseColor.BLACK);

        for (String encabezado : encabezados) {
            PdfPCell celda = new PdfPCell(new Phrase(encabezado, fuenteCabecera));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celda.setBackgroundColor(BaseColor.DARK_GRAY);
            celda.setPadding(6);
            tabla.addCell(celda);
        }

        for (List<String> fila : datos) {
            for (String dato : fila) {
                PdfPCell celda = new PdfPCell(new Phrase(dato != null ? dato : "", fuenteDatos));
                celda.setPadding(5);
                celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tabla.addCell(celda);
            }
        }

        documento.add(tabla);
        documento.add(new Paragraph("\n"));
    }

    private static void crearPortada(Document documento, String titulo) throws DocumentException {
        documento.add(new Paragraph("\n"));

        Paragraph universidad = new Paragraph("UNIVERSIDAD VERACRUZANA", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, BaseColor.DARK_GRAY));
        universidad.setAlignment(Element.ALIGN_CENTER);
        documento.add(universidad);

        Paragraph facultad = new Paragraph("Facultad de Estadística e Informática", FontFactory.getFont(FontFactory.HELVETICA, 16, BaseColor.GRAY));
        facultad.setAlignment(Element.ALIGN_CENTER);
        documento.add(facultad);
        
        Paragraph sistema = new Paragraph("Sistema de Gestión de Tutorías", FontFactory.getFont(FontFactory.HELVETICA, 14, BaseColor.GRAY));
        sistema.setAlignment(Element.ALIGN_CENTER);
        documento.add(sistema);

        documento.add(new Paragraph("\n\n"));

        Paragraph tituloRep = new Paragraph(titulo, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20));
        tituloRep.setAlignment(Element.ALIGN_CENTER);
        documento.add(tituloRep);

        documento.add(new Paragraph("\n\n"));

        Paragraph fecha = new Paragraph("Fecha de impresión: " + LocalDate.now(), FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10));
        fecha.setAlignment(Element.ALIGN_RIGHT);
        documento.add(fecha);
        
        documento.add(new Paragraph("\n"));
    }

    static class Membrete extends PdfPageEventHelper {
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER, new Phrase("Universidad Veracruzana - Reporte General de Tutorías", FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.GRAY)),
                    (document.right() - document.left()) / 2 + document.leftMargin(),
                    document.top() + 10, 0);

            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER, new Phrase(String.format("Página %d", writer.getPageNumber()), FontFactory.getFont(FontFactory.HELVETICA, 8)),
                    (document.right() - document.left()) / 2 + document.leftMargin(),
                    document.bottom() - 10, 0);
        }
    }
}