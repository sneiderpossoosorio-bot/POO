/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.appnequi;

/**
 *
 * @author janny
 */
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import java.io.File;
import java.io.IOException;
import java.awt.Color;

public class FacturaPDFBox {
    
    private static final String FACTURAS_DIR = "facturas_pdf";
    private static final float MARGIN = 50;
    private static final float FONT_SIZE_TITLE = 18;
    private static final float FONT_SIZE_SUBTITLE = 14;
    private static final float FONT_SIZE_NORMAL = 12;
    private static final float FONT_SIZE_SMALL = 10;
    private static final float LINE_HEIGHT = 15;
    
    // Crear directorio de facturas si no existe
    static {
        File dir = new File(FACTURAS_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    public static String generarFacturaPDF(Factura factura) {
        String nombreArchivo = FACTURAS_DIR + File.separator + "Factura_" + factura.getNumeroFactura() + ".pdf";
        
        try {
            // Crear documento PDF
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            
            // Crear stream de contenido
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            
            float yPosition = page.getMediaBox().getHeight() - MARGIN;
            
            // Título principal
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, FONT_SIZE_TITLE);
            contentStream.newLineAtOffset(MARGIN + 150, yPosition);
            contentStream.showText("FACTURA NEQUI");
            contentStream.endText();
            
            yPosition -= 40;
            
            // Información de la empresa
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, FONT_SIZE_SMALL);
            contentStream.newLineAtOffset(MARGIN + 120, yPosition);
            contentStream.showText("NequiApp - Servicios Financieros Digitales");
            contentStream.endText();
            
            yPosition -= LINE_HEIGHT;
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, FONT_SIZE_SMALL);
            contentStream.newLineAtOffset(MARGIN + 180, yPosition);
            contentStream.showText("Bogota, Colombia");
            contentStream.endText();
            
            yPosition -= LINE_HEIGHT;
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, FONT_SIZE_SMALL);
            contentStream.newLineAtOffset(MARGIN + 170, yPosition);
            contentStream.showText("NIT: 900.123.456-7");
            contentStream.endText();
            
            yPosition -= 40;
            
            // Línea separadora
            contentStream.setStrokingColor(Color.BLACK);
            contentStream.setLineWidth(1f);
            contentStream.moveTo(MARGIN, yPosition);
            contentStream.lineTo(page.getMediaBox().getWidth() - MARGIN, yPosition);
            contentStream.stroke();
            
            yPosition -= 30;
            
            // Información de la factura
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, FONT_SIZE_SUBTITLE);
            contentStream.newLineAtOffset(MARGIN, yPosition);
            contentStream.showText("INFORMACION DE LA FACTURA");
            contentStream.endText();
            
            yPosition -= 25;
            
            // Datos de la factura
            String[][] datosFactura = {
                {"Numero de Factura:", factura.getNumeroFactura()},
                {"Fecha y Hora:", factura.getFechaHora()},
                {"Cliente:", factura.getNombreCliente()},
                {"Tipo de Operacion:", factura.getTipoOperacion()}
            };
            
            for (String[] dato : datosFactura) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, FONT_SIZE_NORMAL);
                contentStream.newLineAtOffset(MARGIN, yPosition);
                contentStream.showText(dato[0]);
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, FONT_SIZE_NORMAL);
                contentStream.newLineAtOffset(MARGIN + 150, yPosition);
                contentStream.showText(dato[1]);
                contentStream.endText();
                
                yPosition -= LINE_HEIGHT + 3;
            }
            
            yPosition -= 20;
            
            // Línea separadora
            contentStream.moveTo(MARGIN, yPosition);
            contentStream.lineTo(page.getMediaBox().getWidth() - MARGIN, yPosition);
            contentStream.stroke();
            
            yPosition -= 30;
            
            // Detalles financieros
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, FONT_SIZE_SUBTITLE);
            contentStream.newLineAtOffset(MARGIN, yPosition);
            contentStream.showText("DETALLES FINANCIEROS");
            contentStream.endText();
            
            yPosition -= 25;
            
            String[][] datosFinancieros = {
                {"Monto de la Operacion:", "$" + String.format("%.2f", factura.getMonto()) + " COP"},
                {"Saldo Anterior:", "$" + String.format("%.2f", factura.getSaldoAnterior()) + " COP"},
                {"Saldo Actual:", "$" + String.format("%.2f", factura.getSaldoActual()) + " COP"}
            };
            
            for (int i = 0; i < datosFinancieros.length; i++) {
                String[] dato = datosFinancieros[i];
                
                // Resaltar saldo actual
                if (i == 2) {
                    // Fondo amarillo para saldo actual
                    contentStream.setNonStrokingColor(Color.YELLOW);
                    contentStream.addRect(MARGIN - 5, yPosition - 3, 400, LINE_HEIGHT + 6);
                    contentStream.fill();
                    contentStream.setNonStrokingColor(Color.BLACK);
                }
                
                contentStream.beginText();
                contentStream.setFont(i == 2 ? PDType1Font.HELVETICA_BOLD : PDType1Font.HELVETICA_BOLD, FONT_SIZE_NORMAL);
                contentStream.newLineAtOffset(MARGIN, yPosition);
                contentStream.showText(dato[0]);
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(i == 2 ? PDType1Font.HELVETICA_BOLD : PDType1Font.HELVETICA, FONT_SIZE_NORMAL);
                contentStream.newLineAtOffset(MARGIN + 150, yPosition);
                contentStream.showText(dato[1]);
                contentStream.endText();
                
                yPosition -= LINE_HEIGHT + 3;
            }
            
            // Detalles adicionales si existen
            if (factura.getDetallesAdicionales() != null && !factura.getDetallesAdicionales().isEmpty()) {
                yPosition -= 20;
                
                // Línea separadora
                contentStream.moveTo(MARGIN, yPosition);
                contentStream.lineTo(page.getMediaBox().getWidth() - MARGIN, yPosition);
                contentStream.stroke();
                
                yPosition -= 30;
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, FONT_SIZE_SUBTITLE);
                contentStream.newLineAtOffset(MARGIN, yPosition);
                contentStream.showText("DETALLES ADICIONALES");
                contentStream.endText();
                
                yPosition -= 25;
                
                // Dividir detalles adicionales en líneas
                String[] lineas = factura.getDetallesAdicionales().split("\n");
                for (String linea : lineas) {
                    if (yPosition < MARGIN + 50) {
                        // Si no hay espacio, crear nueva página
                        contentStream.close();
                        page = new PDPage(PDRectangle.A4);
                        document.addPage(page);
                        contentStream = new PDPageContentStream(document, page);
                        yPosition = page.getMediaBox().getHeight() - MARGIN;
                    }
                    
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, FONT_SIZE_SMALL);
                    contentStream.newLineAtOffset(MARGIN, yPosition);
                    contentStream.showText(linea);
                    contentStream.endText();
                    
                    yPosition -= LINE_HEIGHT;
                }
            }
            
            // Pie de página
            yPosition = MARGIN + 80;
            
            // Línea separadora final
            contentStream.moveTo(MARGIN, yPosition);
            contentStream.lineTo(page.getMediaBox().getWidth() - MARGIN, yPosition);
            contentStream.stroke();
            
            yPosition -= 25;
            
            String[] piePagina = {
                "Gracias por usar NequiApp!",
                "Esta factura es un comprobante valido de su transaccion.",
                "Para soporte tecnico contacte: soporte@nequiapp.com"
            };
            
            for (String linea : piePagina) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, FONT_SIZE_SMALL);
                float textWidth = PDType1Font.HELVETICA.getStringWidth(linea) / 1000 * FONT_SIZE_SMALL;
                float centeredX = (page.getMediaBox().getWidth() - textWidth) / 2;
                contentStream.newLineAtOffset(centeredX, yPosition);
                contentStream.showText(linea);
                contentStream.endText();
                
                yPosition -= LINE_HEIGHT;
            }
            
            // Cerrar stream y guardar documento
            contentStream.close();
            document.save(nombreArchivo);
            document.close();
            
            return nombreArchivo;
            
        } catch (IOException e) {
            System.err.println("Error al generar el PDF: " + e.getMessage());
            return null;
        }
    }
    
    // Método para obtener la ruta completa del archivo
    public static String obtenerRutaCompleta(String nombreArchivo) {
        File archivo = new File(nombreArchivo);
        return archivo.getAbsolutePath();
    }
}
