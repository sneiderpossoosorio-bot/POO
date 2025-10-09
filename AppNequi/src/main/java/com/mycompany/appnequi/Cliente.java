/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.appnequi;

/**
 *
 * @author janny
 */
// HERENCIA: Cliente hereda de Persona
// COMPOSICIÓN: Cliente tiene una Cuenta
// AGREGACIÓN: Cliente puede tener una MetaAhorro

public class Cliente extends Persona {
    private Cuenta cuenta;       // Composición
    private MetaAhorro meta;     // Agregación

    public Cliente(String nombre, String documento, Cuenta cuenta) {
        super(nombre, documento); // Constructor de Persona
        this.cuenta = cuenta;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void crearMeta(String nombreMeta, double objetivo) {
        this.meta = new MetaAhorro(nombreMeta, objetivo);
        System.out.println(" Meta creada para " + getNombre() + ": " + nombreMeta + " (" + objetivo + " COP)");
    }

    public MetaAhorro getMeta() {
        return meta;
    }

    // Mostrar info completa del cliente
    public void mostrarInfo() {
        System.out.println("================================");
        mostrarPersona(); // de Persona
        System.out.println("Saldo actual: " + cuenta.getSaldo() + " COP");
        if (meta != null) {
            meta.mostrarProgreso();
        }
        System.out.println("================================");
    }
}