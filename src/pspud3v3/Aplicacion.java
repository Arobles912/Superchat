/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pspud3v3;

import InterfazGrafica.VistaLogin;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Angel
 */
/**public class Aplicacion {

    private static final int PUERTO_SERVIDOR = 49175;
    private SocketTCPServerV5 servidor;

    public Aplicacion() {
        try {
            servidor = new SocketTCPServerV5(PUERTO_SERVIDOR);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void iniciarServidor() {
        servidor.start(); // Iniciamos el servidor aquí
    }

    public void mostrarVentanaLogin() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VistaLogin vistaLogin = new VistaLogin();
                    vistaLogin.setVisible(true);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        Aplicacion aplicacion = new Aplicacion();
        aplicacion.mostrarVentanaLogin();
        aplicacion.iniciarServidor();

        
    }
    // Abre otras ventanas según sea necesario
}**/


