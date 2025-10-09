/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.appnequi;

/**
 *
 * @author janny
 */
import java.util.HashMap;

// ASOCIACIÓN: Esta clase solo usa Cuenta, no depende directamente
public class TransferenciaInternacional {
    private static final HashMap<String, Double> TASA_CAMBIO = new HashMap<>(); // Tabla de tasas
    private static final double COMISION = 0.05; // Comisión del 5%

    // Inicializar tasas de cambio
    static {
        TASA_CAMBIO.put("USD", 4000.0);
        TASA_CAMBIO.put("EUR", 4300.0);
        TASA_CAMBIO.put("GBP", 5000.0);
        TASA_CAMBIO.put("MXN", 250.0);
        TASA_CAMBIO.put("PEN", 1100.0);
        TASA_CAMBIO.put("CLP", 5.0);
    }

    // Método para realizar transferencia internacional
    public static Factura transferir(Cuenta cuentaOrigen, double monto, String divisa, String bancoDestino, String nombreCliente) {
        divisa = divisa.toUpperCase();
        if (!TASA_CAMBIO.containsKey(divisa)) {
            System.out.println(" Divisa no soportada: " + divisa);
            System.out.println("Disponibles: " + TASA_CAMBIO.keySet());
            return null;
        }

        double tasa = TASA_CAMBIO.get(divisa);   // Valor de 1 unidad en COP
        double montoCOP = monto * tasa;          // Conversión a pesos
        double comision = montoCOP * COMISION;   // Comisión aplicada
        double montoFinal = montoCOP - comision; // Dinero final que llega
        double saldoAnterior = cuentaOrigen.getSaldo();

        if (cuentaOrigen.retirarInterno(montoCOP)) {
            System.out.println(" Transferencia internacional exitosa:");
            System.out.println("Banco destino: " + bancoDestino);
            System.out.println("Divisa enviada: " + monto + " " + divisa);
            System.out.println("Monto convertido: " + montoCOP + " COP");
            System.out.println("Comisión: " + comision + " COP");
            System.out.println("Monto final recibido: " + montoFinal + " COP");
            
            // Generar factura con detalles adicionales
            String detalles = "Banco destino: " + bancoDestino + "\n" +
                            "Divisa enviada: " + monto + " " + divisa + "\n" +
                            "Tasa de cambio: 1 " + divisa + " = " + tasa + " COP\n" +
                            "Comisión (5%): $" + String.format("%.2f", comision) + " COP\n" +
                            "Monto final recibido: $" + String.format("%.2f", montoFinal) + " COP";
            
            Factura factura = new Factura("TRANSFERENCIA INTERNACIONAL", montoCOP, saldoAnterior, cuentaOrigen.getSaldo(), nombreCliente, detalles);
            return factura;
        } else {
            System.out.println(" Fondos insuficientes para la transferencia.");
            return null;
        }
    }

    // Mostrar todas las tasas de cambio disponibles
    public static void mostrarTasas() {
        System.out.println(" Tasas de cambio (1 unidad en COP):");
        for (String divisa : TASA_CAMBIO.keySet()) {
            System.out.println(divisa + " = " + TASA_CAMBIO.get(divisa));
        }
    }
}