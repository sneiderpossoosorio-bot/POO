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

// AGREGACIÓN: Un banco tiene varios clientes
public class Banco {
    private String nombre;
    private ArrayList<Cliente> clientes;

    public Banco(String nombre) {
        this.nombre = nombre;
        this.clientes = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void agregarCliente(Cliente cliente) {
        clientes.add(cliente);
        System.out.println(" Cliente " + cliente.getNombre() + " agregado al banco " + nombre);
    }

    public void mostrarClientes() {
        System.out.println(" Clientes en el banco " + nombre + ":");
        for (Cliente c : clientes) {
            c.mostrarInfo();
        }
    }
}