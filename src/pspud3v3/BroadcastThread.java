/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pspud3v3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Angel
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BroadcastThread extends Thread {

    private SocketTCPServerV5 server; // Referencia al servidor

    // Constructor de la clase
    public BroadcastThread(SocketTCPServerV5 server) {
        this.server = server; // Asigna el servidor proporcionado
    }

    // Método run que se ejecuta cuando se inicia el hilo
    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // Crea un lector de flujo de entrada para recibir mensajes desde la consola
            String mensaje; // Variable para almacenar el mensaje introducido
            while ((mensaje = br.readLine()) != null) { // Lee mensajes desde la consola hasta que no haya más datos
                server.broadcastMensaje(mensaje); // Llama al método en SocketTCPServerV5 para transmitir el mensaje a todos los clientes conectados
            }
        } catch (IOException e) {
            e.printStackTrace(); // Imprime cualquier excepción que ocurra durante la lectura de mensajes desde la consola
        }
    }
}
