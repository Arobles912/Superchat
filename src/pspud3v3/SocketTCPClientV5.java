/* Modificaciones sobre la primera versión
    - Se envían mensajes en lugar de bytes.
 */
package pspud3v3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketTCPClientV5 {

    private String serverIP; // IP del servidor al que se conectará el cliente
    private int serverPort; // Puerto del servidor al que se conectará el cliente
    private String userName; // Nombre de usuario del cliente
    private Socket socket; // Socket para la conexión con el servidor

    // Constructor que inicializa los atributos del cliente
    public SocketTCPClientV5(String serverIP, int serverPort, String userName) {
        this.serverIP = serverIP; // Asigna la IP del servidor
        this.serverPort = serverPort; // Asigna el puerto del servidor
        this.userName = userName; // Asigna el nombre de usuario
    }

    // Método para iniciar la conexión con el servidor
    public void start() {
        try {
            // Crea un nuevo socket para la conexión con el servidor
            socket = new Socket(serverIP, serverPort);
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true); // Crea un PrintWriter para enviar datos al servidor
            Scanner scanner = new Scanner(System.in); // Crea un Scanner para leer la entrada del usuario desde la consola

            // Envía el nombre de usuario al servidor
            pw.println(userName);

            System.out.println("Conexión establecida con el servidor."); // Imprime un mensaje indicando que la conexión se ha establecido

            String message;
            // Ciclo para permitir al usuario enviar mensajes al servidor
            do {
                System.out.print("Mensaje a enviar (END para terminar): "); // Solicita al usuario que ingrese un mensaje
                message = scanner.nextLine(); // Lee el mensaje ingresado por el usuario desde la consola
                enviarMensaje(message); // Llama al método para enviar el mensaje al servidor
            } while (!message.equals("END")); // El ciclo se ejecuta hasta que el usuario ingrese "END" para terminar la conexión

            socket.close(); // Cierra el socket al finalizar la conexión
            System.out.println("Conexión cerrada."); // Imprime un mensaje indicando que la conexión se ha cerrado

        } catch (IOException e) {
            e.printStackTrace(); // Maneja cualquier excepción de E/S
        }
    }

    // Método getter para obtener el socket del cliente
    public Socket getSocket() {
        return socket; // Devuelve el socket del cliente
    }

    // Método para enviar un mensaje al servidor a través del socket
    public void enviarMensaje(String mensaje) {
        try {
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true); // Crea un PrintWriter para enviar datos al servidor
            pw.println(mensaje); // Envía el mensaje al servidor
        } catch (IOException e) {
            e.printStackTrace(); // Maneja cualquier excepción de E/S
        }
    }

    // Método main para iniciar el cliente
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Crea un Scanner para leer la entrada del usuario desde la consola
        System.out.println("Introduce tu nombre de usuario: "); // Solicita al usuario que ingrese su nombre de usuario
        String userName = scanner.nextLine(); // Lee el nombre de usuario ingresado por el usuario desde la consola
        // Crea una instancia del cliente con la IP del servidor, el puerto del servidor y el nombre de usuario ingresados por el usuario
        SocketTCPClientV5 client = new SocketTCPClientV5("localhost", 49175, userName);
        client.start(); // Inicia el cliente
    }
}
