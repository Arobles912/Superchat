/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pspud3v3;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Angel
 */
import java.io.*;
import java.net.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GestorProcesos extends Thread {

    private Socket socket; // Socket para la conexión con el cliente
    private BufferedReader reader; // BufferedReader para leer datos del cliente
    private PrintWriter writer; // PrintWriter para enviar datos al cliente
    private final ArrayList<Integer> generatedNumbers = new ArrayList<>(); // Lista para almacenar números generados
    private SocketTCPServerV5 server; // Referencia al servidor
    private BufferedWriter bw; // BufferedWriter para escribir en el archivo de registro
    private String userName; // Nombre de usuario del cliente

    // Constructor que inicializa los atributos del gestor de procesos
    public GestorProcesos(Socket socket, SocketTCPServerV5 server, String userName, BufferedWriter bw) {
        this.socket = socket; // Asigna el socket de la conexión con el cliente
        this.server = server; // Asigna la referencia al servidor
        this.userName = userName; // Asigna el nombre de usuario del cliente
        this.bw = bw; // Asigna el BufferedWriter para escribir en el archivo de registro
    }

    // Método run que se ejecuta cuando se inicia el hilo
    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Crea un BufferedReader para leer datos del cliente
            writer = new PrintWriter(socket.getOutputStream(), true); // Crea un PrintWriter para enviar datos al cliente
            String identificadorCliente = socket.getInetAddress().getHostAddress(); // Obtiene la dirección IP del cliente
            System.out.println("Conexión establecida con cliente " + userName + " (" + identificadorCliente + ")"); // Imprime un mensaje indicando que se ha establecido la conexión con el cliente

            String message;
            // Bucle para recibir y procesar mensajes del cliente
            while ((message = reader.readLine()) != null && !message.equals("END")) {
                LocalDateTime now = LocalDateTime.now(); // Obtiene la fecha y hora actual
                String pattern = "dd/MM/yyyy HH:mm:ss"; // Formato de la fecha y hora
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern); // Crea un formateador de fecha y hora
                String formattedTimestamp = now.format(formatter); // Formatea la fecha y hora actual

                String outputMessage = formattedTimestamp + " - Cliente " + userName + " (" + identificadorCliente + "): " + message; // Crea el mensaje a enviar al servidor y guardar en el archivo de registro
                System.out.println(outputMessage); // Imprime el mensaje en la consola

                server.broadcastMensaje(outputMessage); // Envía el mensaje a todos los clientes conectados al servidor

                bw.write(outputMessage); // Escribe el mensaje en el archivo de registro
                bw.newLine(); // Agrega un salto de línea al archivo de registro
                bw.flush(); // Limpia el buffer de escritura para asegurar que los datos se escriban en el archivo

                // Continúa recibiendo mensajes del cliente
            }

            System.out.println("Conexión cerrada con cliente " + userName + " " + identificadorCliente); // Imprime un mensaje indicando que se ha cerrado la conexión con el cliente

        } catch (IOException e) {
            e.printStackTrace(); // Maneja cualquier excepción de E/S
        } finally {
            try {
                socket.close(); // Cierra el socket de la conexión con el cliente
                bw.close(); // Cierra el BufferedWriter al finalizar el hilo
            } catch (IOException e) {
                e.printStackTrace(); // Maneja cualquier excepción de E/S
            }
        }
    }

    // Método para enviar un mensaje al cliente a través del PrintWriter
    public void enviarMensaje(String mensaje) {
        writer.println(mensaje); // Envía el mensaje al cliente
    }

}
