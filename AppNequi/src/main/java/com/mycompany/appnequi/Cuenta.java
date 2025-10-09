/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.appnequi;

/**
 *
 * @author janny
 */
import java.util.ArrayList;

public class Cuenta {
    private double saldo;                 // Saldo disponible
    private ArrayList<String> historial;  // Registro de transacciones

    // Constructor: inicializa saldo e historial
    public Cuenta(double saldoInicial) {
        this.saldo = saldoInicial;
        this.historial = new ArrayList<>();
        historial.add("Cuenta creada con saldo inicial: " + saldoInicial);
    }

    // Getter para saldo
    public double getSaldo() {
        return saldo;
    }

    // Depositar dinero en la cuenta
    public Factura depositar(double monto, String nombreCliente) {
        double saldoAnterior = saldo;
        saldo += monto;
        historial.add("Deposito de: " + monto);
        
        // Generar factura
        Factura factura = new Factura("DEPÓSITO", monto, saldoAnterior, saldo, nombreCliente);
        return factura;
    }

    // Retirar dinero de la cuenta (si hay fondos suficientes)
    public Factura retirar(double monto, String nombreCliente) {
        if (saldo >= monto) {
            double saldoAnterior = saldo;
            saldo -= monto;
            historial.add("Retiro de: " + monto);
            
            // Generar factura
            Factura factura = new Factura("RETIRO", monto, saldoAnterior, saldo, nombreCliente);
            return factura;
        }
        return null; // No se pudo realizar el retiro
    }
    
    // Método auxiliar para retiros internos (sin factura)
    public boolean retirarInterno(double monto) {
        if (saldo >= monto) {
            saldo -= monto;
            historial.add("Retiro de: " + monto);
            return true;
        }
        return false;
    }

    // Mostrar todo el historial de transacciones
    public void mostrarHistorial() {
        System.out.println(" Historial de transacciones:");
        for (String h : historial) {
            System.out.println("- " + h);
        }
    }
}
