/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.appnequi;

/**
 *
 * @author janny
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Factura {
    private String numeroFactura;
    private String tipoOperacion;
    private double monto;
    private double saldoAnterior;
    private double saldoActual;
    private String fechaHora;
    private String nombreCliente;
    private String detallesAdicionales;
    
    // Constructor para operaciones básicas (depósito, retiro)
    public Factura(String tipoOperacion, double monto, double saldoAnterior, double saldoActual, String nombreCliente) {
        this.numeroFactura = generarNumeroFactura();
        this.tipoOperacion = tipoOperacion;
        this.monto = monto;
        this.saldoAnterior = saldoAnterior;
        this.saldoActual = saldoActual;
        this.nombreCliente = nombreCliente;
        this.fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        this.detallesAdicionales = "";
    }
    
    // Constructor para operaciones con detalles adicionales (transferencias, metas)
    public Factura(String tipoOperacion, double monto, double saldoAnterior, double saldoActual, 
                   String nombreCliente, String detallesAdicionales) {
        this(tipoOperacion, monto, saldoAnterior, saldoActual, nombreCliente);
        this.detallesAdicionales = detallesAdicionales;
    }
    
    // Generar número de factura único
    private String generarNumeroFactura() {
        Random random = new Random();
        return "FAC-" + System.currentTimeMillis() + "-" + random.nextInt(1000);
    }
    
    // Mostrar la factura completa
    public void mostrarFactura() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("                  FACTURA NEQUI");
        System.out.println("=".repeat(50));
        System.out.println("Número de Factura: " + numeroFactura);
        System.out.println("Fecha y Hora: " + fechaHora);
        System.out.println("Cliente: " + nombreCliente);
        System.out.println("-".repeat(50));
        System.out.println("Tipo de Operación: " + tipoOperacion);
        System.out.println("Monto: $" + String.format("%.2f", monto) + " COP");
        System.out.println("Saldo Anterior: $" + String.format("%.2f", saldoAnterior) + " COP");
        System.out.println("Saldo Actual: $" + String.format("%.2f", saldoActual) + " COP");
        
        if (!detallesAdicionales.isEmpty()) {
            System.out.println("-".repeat(50));
            System.out.println("Detalles Adicionales:");
            System.out.println(detallesAdicionales);
        }
        
        System.out.println("-".repeat(50));
        System.out.println("¡Gracias por usar NequiApp!");
        System.out.println("=".repeat(50));
    }
    
    // Getters
    public String getNumeroFactura() {
        return numeroFactura;
    }
    
    public String getTipoOperacion() {
        return tipoOperacion;
    }
    
    public double getMonto() {
        return monto;
    }
    
    public String getFechaHora() {
        return fechaHora;
    }
    
    public String getNombreCliente() {
        return nombreCliente;
    }
    
    public double getSaldoAnterior() {
        return saldoAnterior;
    }
    
    public double getSaldoActual() {
        return saldoActual;
    }
    
    public String getDetallesAdicionales() {
        return detallesAdicionales;
    }
    
    // Método para generar factura en PDF usando Apache PDFBox
    public String generarPDF() {
        try {
            // Usar reflexión para evitar errores de compilación cuando PDFBox no está disponible
            Class<?> pdfBoxClass = Class.forName("com.mycompany.appnequi.FacturaPDFBox");
            java.lang.reflect.Method generarMethod = pdfBoxClass.getMethod("generarFacturaPDF", Factura.class);
            java.lang.reflect.Method rutaMethod = pdfBoxClass.getMethod("obtenerRutaCompleta", String.class);
            
            String rutaArchivo = (String) generarMethod.invoke(null, this);
            if (rutaArchivo != null) {
                System.out.println("\n[+] Factura PDF generada exitosamente:");
                System.out.println("[*] Ubicacion: " + rutaMethod.invoke(null, rutaArchivo));
                System.out.println("[!] Generado con Apache PDFBox 2.0.29");
                return rutaArchivo;
            } else {
                System.out.println("[-] Error al generar la factura PDF");
                return null;
            }
        } catch (ClassNotFoundException e) {
            System.out.println("[!] FacturaPDFBox no encontrada. Compilar con dependencias PDFBox.");
            return null;
        } catch (Exception e) {
            System.out.println("[!] Error al generar PDF: " + e.getMessage());
            System.out.println("[!] Para usar PDF, ejecuta: descargar_dependencias.bat");
            return null;
        }
    }
    
    // Método para generar factura en archivo de texto (respaldo simple)
    public String generarArchivoTexto() {
        try {
            String nombreArchivo = "facturas_txt/Factura_" + numeroFactura + ".txt";
            java.io.File dir = new java.io.File("facturas_txt");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            
            java.io.FileWriter writer = new java.io.FileWriter(nombreArchivo);
            writer.write("==================================================\n");
            writer.write("                  FACTURA NEQUI\n");
            writer.write("==================================================\n");
            writer.write("Numero de Factura: " + numeroFactura + "\n");
            writer.write("Fecha y Hora: " + fechaHora + "\n");
            writer.write("Cliente: " + nombreCliente + "\n");
            writer.write("Tipo de Operacion: " + tipoOperacion + "\n");
            writer.write("Monto: $" + String.format("%.2f", monto) + " COP\n");
            writer.write("Saldo Anterior: $" + String.format("%.2f", saldoAnterior) + " COP\n");
            writer.write("Saldo Actual: $" + String.format("%.2f", saldoActual) + " COP\n");
            if (detallesAdicionales != null && !detallesAdicionales.isEmpty()) {
                writer.write("\nDetalles Adicionales:\n" + detallesAdicionales + "\n");
            }
            writer.write("==================================================\n");
            writer.close();
            
            System.out.println("\n[+] Factura TXT generada exitosamente:");
            System.out.println("[*] Ubicacion: " + new java.io.File(nombreArchivo).getAbsolutePath());
            return nombreArchivo;
        } catch (java.io.IOException e) {
            System.out.println("[-] Error al generar la factura TXT: " + e.getMessage());
            return null;
        }
    }
    
    // Método para mostrar factura en consola Y generar PDF
    public void mostrarFacturaCompleta() {
        // Mostrar en consola
        mostrarFactura();
        
        // Intentar generar PDF, si falla generar TXT como respaldo
        String rutaPDF = generarPDF();
        if (rutaPDF == null) {
            System.out.println("[!] Generando respaldo en formato TXT...");
            generarArchivoTexto();
        }
    }
}
