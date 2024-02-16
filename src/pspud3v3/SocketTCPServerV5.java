/* Modificaciones sobre la primera versión
    - Se envían mensajes en lugar de bytes.
    - Se informa de la ip del cliente
    - Se aceptan multiples conexiones y se identifican los mensajes.

Queda Pendiente:
    x Atender conexiones en un puerto, gestionar el servicio en otro.
    x El servidor guardará el listado de las conexiones activas.
    x Los mensajes recibidos se reenviarán a todos los clientes.
    x Los clientes podrán realizar las siguientes acciones:
            * Enviar mensajes.
            * Conectarse.
            * Desconectarse.
            - Cambiar su nombre de usuario.
            - Consultar usuarios conectados.
 */
package pspud3v3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketTCPServerV5 {

    private ServerSocket serverSocket; // Socket del servidor para aceptar conexiones entrantes
    private List<GestorProcesos> gestores; // Lista de hilos que gestionan las conexiones con los clientes
    private BroadcastThread broadcastThread; // Hilo para enviar mensajes a todos los clientes
    public File file; // Archivo para almacenar el historial de mensajes
    public BufferedWriter bw; // BufferedWriter para escribir en el archivo

    // Constructor que inicializa el servidor en el puerto especificado
    public SocketTCPServerV5(int puerto) throws IOException {
        serverSocket = new ServerSocket(puerto); // Crea un nuevo ServerSocket en el puerto dado
        gestores = new ArrayList<>(); // Inicializa la lista de gestores de procesos
        broadcastThread = new BroadcastThread(this); // Inicializa el hilo de difusión de mensajes
        try {
            LocalDateTime now = LocalDateTime.now(); // Obtiene la fecha y hora actual
            // Crea el nombre del archivo de historial usando la fecha y hora actual
            String fileName = "ficheros/historial" + now.getDayOfMonth() + now.getMonthValue() + now.getYear() + "_" + now.getHour() + now.getMinute() + now.getSecond() + ".log";
            file = new File(fileName); // Crea el archivo con el nombre generado
            bw = new BufferedWriter(new FileWriter(file)); // Crea el BufferedWriter para escribir en el archivo
        } catch (IOException e) {
            e.printStackTrace(); // Maneja cualquier excepción al crear el archivo
        }
    }

    // Método para iniciar el servidor y aceptar conexiones entrantes
    public void start() {
        try {
            System.out.println("Sala Abierta."); // Imprime un mensaje indicando que la sala está abierta

            broadcastThread.start(); // Inicia el hilo de difusión de mensajes

            // Bucle para aceptar conexiones entrantes continuamente
            while (true) {
                Socket socket = serverSocket.accept(); // Acepta una conexión entrante y crea un nuevo Socket para manejarla

                // Lee el nombre de usuario del cliente desde el flujo de entrada del socket
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String userName = reader.readLine();

                // Crea un nuevo GestorProcesos para manejar la conexión con el cliente
                GestorProcesos gestor = new GestorProcesos(socket, this, userName, bw);
                gestores.add(gestor); // Agrega el gestor a la lista de gestores
                gestor.start(); // Inicia el hilo del gestor
            }
        } catch (IOException ioe) {
            ioe.printStackTrace(); // Maneja cualquier excepción de E/S
        } finally{
            try {
                bw.close(); // Cierra el BufferedWriter cuando se finaliza el servidor
            } catch (IOException ex) {
               ex.printStackTrace(); // Maneja cualquier excepción al cerrar el BufferedWriter
            }
        }
    }

    // Método para difundir un mensaje a todos los clientes conectados
    public synchronized void broadcastMensaje(String mensaje) {
        for (GestorProcesos gestor : gestores) {
            gestor.enviarMensaje(mensaje); // Envía el mensaje a cada gestor de procesos
        }
    }

    // Método main para iniciar el servidor
    public static void main(String[] args) {
        try {
            SocketTCPServerV5 servidor = new SocketTCPServerV5(49175); // Crea una instancia del servidor en el puerto 49175
            servidor.start(); // Inicia el servidor
        } catch (IOException ioe) {
            ioe.printStackTrace(); // Maneja cualquier excepción al iniciar el servidor
        }
    }
}
