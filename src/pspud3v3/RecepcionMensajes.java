/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pspud3v3;

import InterfazGrafica.VistaChat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author Angel
 */
public class RecepcionMensajes implements Runnable {

    private Socket socket; // Socket para la comunicación
    private String respuesta; // Mensaje recibido
    private VistaChat vistaChat; // Referencia a la interfaz de chat

    // Constructor de la clase
    public RecepcionMensajes(Socket socket, VistaChat vistaChat) {
        this.socket = socket; // Asigna el socket proporcionado
        this.vistaChat = vistaChat; // Asigna la referencia a la interfaz de chat proporcionada
    }

    // Método run que se ejecuta cuando se inicia el hilo
    @Override
    public void run() {
        try {
            BufferedReader br2 = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Crea un lector de flujo de entrada para recibir mensajes
            while ((respuesta = br2.readLine()) != null) { // Lee mensajes del flujo de entrada hasta que no haya más datos
                vistaChat.agregarMensaje(respuesta); // Llama al método en VistaChat para agregar el mensaje recibido a la interfaz gráfica de chat
            }
        } catch (IOException e) {
            e.printStackTrace(); // Imprime cualquier excepción que ocurra durante la lectura de mensajes
        }
    }

    // Método getter para obtener la última respuesta recibida
    public String getRespuesta() {
        return respuesta;
    }
}

