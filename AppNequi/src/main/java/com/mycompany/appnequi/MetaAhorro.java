/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.appnequi;

/**
 *
 * @author janny
 */
public class MetaAhorro {
    private String nombreMeta; // Nombre de la meta
    private double objetivo;   // Monto objetivo de la meta
    private double ahorrado;   // Monto ahorrado hasta el momento

    // Constructor
    public MetaAhorro(String nombreMeta, double objetivo) {
        this.nombreMeta = nombreMeta;
        this.objetivo = objetivo;
        this.ahorrado = 0;
    }

    // Apartar dinero para la meta
    public Factura apartarDinero(double monto, String nombreCliente, double saldoAnterior, double saldoActual) {
        ahorrado += monto;
        System.out.println(" Se apartaron " + monto + " a la meta " + nombreMeta);
        mostrarProgreso();
        
        // Generar factura con detalles de la meta
        String detalles = "Meta de ahorro: " + nombreMeta + "\n" +
                         "Objetivo: $" + String.format("%.2f", objetivo) + " COP\n" +
                         "Total ahorrado: $" + String.format("%.2f", ahorrado) + " COP\n" +
                         "Progreso: " + String.format("%.1f", (ahorrado/objetivo)*100) + "%";
        
        Factura factura = new Factura("AHORRO EN META", monto, saldoAnterior, saldoActual, nombreCliente, detalles);
        return factura;
    }

    // Mostrar el progreso actual de la meta
    public void mostrarProgreso() {
        double restante = objetivo - ahorrado;
        if (restante <= 0) {
            System.out.println(" ¡Meta " + nombreMeta + " alcanzada!");
        } else {
            System.out.println("Progreso en " + nombreMeta + ": " + ahorrado + "/" + objetivo +
                               " (Faltan " + restante + ")");
        }
    }
}