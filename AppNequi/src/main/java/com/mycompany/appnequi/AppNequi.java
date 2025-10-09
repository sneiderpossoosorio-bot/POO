/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.appnequi;

/**
 *
 * @author janny
 */
import java.util.Scanner;

public class AppNequi {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // ===============================
        // CREAR BANCOS (AGREGACIÓN)
        // ===============================
        Banco bancoA = new Banco("Banco de Bogota");
        Banco bancoB = new Banco("Bancolombia");
        Banco bancoC = new Banco("Davivienda");

        // ===============================
        // CREAR CLIENTE (HERENCIA + COMPOSICION)
        // ===============================
        Cuenta cuentaCarlos  = new Cuenta(2000000.0);   // Composicion: Cliente tiene Cuenta
        Cliente carlos = new Cliente("Carlos", "11185363721", cuentaCarlos);

        // Agregar cliente a un banco (AGREGACION: Banco tiene clientes)
        bancoA.agregarCliente(carlos);

        int opcion;
        do {
            // Menú interactivo
            System.out.println("\n===== MENU NEQUI =====");
            System.out.println("1. Mostrar informacion cliente");
            System.out.println("2. Depositar dinero");
            System.out.println("3. Retirar dinero");
            System.out.println("4. Crear meta de ahorro");
            System.out.println("5. Apartar dinero a meta");
            System.out.println("6. Ver historial de transacciones");
            System.out.println("7. Consultar tasas de cambio");
            System.out.println("8. Transferencia internacional");
            System.out.println("9. Mostrar clientes del Banco de Bogota");
            System.out.println("10. Ver carpeta de facturas");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = sc.nextInt();

            // Selección de opciones del menú
            switch (opcion) {
                case 1:
                    carlos.mostrarInfo();
                    break;
                case 2:
                    System.out.print("Ingrese monto a depositar: ");
                    double dep = sc.nextDouble();
                    Factura facturaDeposito = carlos.getCuenta().depositar(dep, carlos.getNombre());
                    System.out.println(" Deposito realizado.");
                    facturaDeposito.mostrarFacturaCompleta();
                    break;
                case 3:
                    System.out.print("Ingrese monto a retirar: ");
                    double ret = sc.nextDouble();
                    Factura facturaRetiro = carlos.getCuenta().retirar(ret, carlos.getNombre());
                    if (facturaRetiro != null) {
                        System.out.println(" Retiro realizado.");
                        facturaRetiro.mostrarFacturaCompleta();
                    } else {
                        System.out.println(" Saldo insuficiente.");
                    }
                    break;
                case 4:
                    System.out.print("Nombre de la meta: ");
                    sc.nextLine(); // Limpieza del buffer
                    String metaNombre = sc.nextLine();
                    System.out.print("Monto objetivo: ");
                    double metaObj = sc.nextDouble();
                    carlos.crearMeta(metaNombre, metaObj);
                    break;
                case 5:
                    if (carlos.getMeta() == null) {
                        System.out.println(" No tienes ninguna meta creada.");
                    } else {
                        System.out.print("Monto a apartar: ");
                        double apartar = sc.nextDouble();
                        double saldoAnterior = carlos.getCuenta().getSaldo();
                        if (carlos.getCuenta().retirarInterno(apartar)) {
                            Factura facturaMeta = carlos.getMeta().apartarDinero(apartar, carlos.getNombre(), saldoAnterior, carlos.getCuenta().getSaldo());
                            facturaMeta.mostrarFacturaCompleta();
                        } else {
                            System.out.println(" Saldo insuficiente.");
                        }
                    }
                    break;
                case 6:
                    carlos.getCuenta().mostrarHistorial();
                    break;
                case 7:
                    TransferenciaInternacional.mostrarTasas();
                    break;
                case 8:
                    System.out.print("Monto a transferir: ");
                    double montoDiv = sc.nextDouble();
                    System.out.print("Divisa (USD, EUR, GBP, MXN, PEN, CLP): ");
                    String divisa = sc.next();
                    System.out.print("Banco destino: ");
                    sc.nextLine();
                    String bancoDestino = sc.nextLine();
                    Factura facturaTransferencia = TransferenciaInternacional.transferir(carlos.getCuenta(), montoDiv, divisa, bancoDestino, carlos.getNombre());
                    if (facturaTransferencia != null) {
                        facturaTransferencia.mostrarFacturaCompleta();
                    }
                    break;
                case 9:
                    bancoA.mostrarClientes();
                    break;
                case 10:
                    mostrarCarpetaFacturas();
                    break;
                case 0:
                    System.out.println(" Gracias por usar NequiApp.");
                    break;
                default:
                    System.out.println(" Opción no valida.");
            }

        } while (opcion != 0);

        sc.close(); // Cerrar el Scanner
    }
    
    // Método para mostrar las facturas generadas
    private static void mostrarCarpetaFacturas() {
        System.out.println("\n[*] FACTURAS GENERADAS:");
        System.out.println("=".repeat(60));
        
        // Mostrar facturas PDF
        java.io.File carpetaPDF = new java.io.File("facturas_pdf");
        if (carpetaPDF.exists()) {
            java.io.File[] archivosPDF = carpetaPDF.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));
            if (archivosPDF != null && archivosPDF.length > 0) {
                System.out.println("[PDF] Ubicacion: " + carpetaPDF.getAbsolutePath());
                System.out.println("[PDF] Total de facturas PDF: " + archivosPDF.length);
                System.out.println("-".repeat(40));
                for (java.io.File archivo : archivosPDF) {
                    System.out.println("[+] " + archivo.getName() + 
                                     " (Tamaño: " + (archivo.length() / 1024) + " KB)");
                }
                System.out.println();
            }
        }
        
        // Mostrar facturas TXT
        java.io.File carpetaTXT = new java.io.File("facturas_txt");
        if (carpetaTXT.exists()) {
            java.io.File[] archivosTXT = carpetaTXT.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
            if (archivosTXT != null && archivosTXT.length > 0) {
                System.out.println("[TXT] Ubicacion: " + carpetaTXT.getAbsolutePath());
                System.out.println("[TXT] Total de facturas TXT: " + archivosTXT.length);
                System.out.println("-".repeat(40));
                for (java.io.File archivo : archivosTXT) {
                    System.out.println("[+] " + archivo.getName() + 
                                     " (Tamaño: " + (archivo.length() / 1024) + " KB)");
                }
                System.out.println();
            }
        }
        
        // Verificar si no hay facturas
        boolean hayFacturas = false;
        if (carpetaPDF.exists()) {
            java.io.File[] pdfFiles = carpetaPDF.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));
            if (pdfFiles != null && pdfFiles.length > 0) hayFacturas = true;
        }
        if (carpetaTXT.exists()) {
            java.io.File[] txtFiles = carpetaTXT.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
            if (txtFiles != null && txtFiles.length > 0) hayFacturas = true;
        }
        
        if (!hayFacturas) {
            System.out.println(" No hay facturas generadas aun.");
        }
        
        System.out.println("=".repeat(60));
        System.out.println("[!] Tip: Los PDFs se abren con cualquier visor de PDF");
        System.out.println("[!] Los TXT se abren con cualquier editor de texto");
        System.out.println("[!] Usando Apache PDFBox 2.0.29 para generar PDFs");
    }
}