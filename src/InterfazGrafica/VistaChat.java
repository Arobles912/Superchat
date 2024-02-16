/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfazGrafica;

import java.awt.Color;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import pspud3v3.SocketTCPClientV5;
import pspud3v3.RecepcionMensajes;

public class VistaChat extends javax.swing.JFrame {

    private static String userName; // Nombre de usuario
    private static SocketTCPClientV5 cliente; // Cliente de socket
    private static RecepcionMensajes recepcionMensajes; // Objeto para recibir mensajes

    // Constructor de la clase VistaChat
    public VistaChat(String userName, SocketTCPClientV5 cliente) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Establece el aspecto del sistema
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace(); // Imprime cualquier error al establecer el aspecto del sistema
        }
        this.cliente = cliente; // Asigna el cliente de socket
        this.userName = userName; // Asigna el nombre de usuario
        initComponents(); // Inicializa los componentes de la interfaz de usuario
        chatroomTitle.setText("Usuario: " + userName); // Establece el título de la sala de chat con el nombre de usuario
        recepcionMensajes = new RecepcionMensajes(cliente.getSocket(), this); // Crea un objeto para recibir mensajes y pasa la referencia de VistaChat
        Thread thread = new Thread(recepcionMensajes); // Crea un hilo para ejecutar el objeto de recepción de mensajes
        thread.start(); // Inicia el hilo
    }

    // Método para agregar un mensaje al panel de mensajes
    public void agregarMensaje(String mensaje) {
        SwingUtilities.invokeLater(() -> messagePanel.append(mensaje + "\n")); // Agrega el mensaje al panel de mensajes de manera segura para hilos
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        messageField = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();
        chatroomTitle = new javax.swing.JLabel();
        logoutButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        messagePanel = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        messageField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                messageFieldActionPerformed(evt);
            }
        });

        sendButton.setText("Enviar");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        chatroomTitle.setFont(new java.awt.Font("Noto Sans", 0, 36)); // NOI18N
        chatroomTitle.setForeground(new java.awt.Color(120, 120, 120));
        chatroomTitle.setText("Usuario:");

        logoutButton.setText("Desconectar");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        messagePanel.setEditable(false);
        messagePanel.setColumns(20);
        messagePanel.setLineWrap(true);
        messagePanel.setRows(5);
        messagePanel.setWrapStyleWord(true);
        jScrollPane1.setViewportView(messagePanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(messageField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(chatroomTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 342, Short.MAX_VALUE)
                        .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(chatroomTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(logoutButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(messageField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Método para manejar el evento de clic en el botón de cerrar sesión
    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        try {
            cliente.getSocket().close(); // Cierra el socket del cliente
            dispose(); // Cierra la ventana de chat
        } catch (IOException ex) {
            ex.printStackTrace(); // Imprime cualquier error al cerrar el socket
        }
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void messageFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_messageFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_messageFieldActionPerformed

    // Método para manejar el evento de clic en el botón de enviar mensaje
    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed

        String mensaje = messageField.getText(); // Obtiene el mensaje del campo de entrada de texto
        cliente.enviarMensaje(mensaje); // Envia el mensaje a través del cliente de socket
        messageField.setText(""); // Limpia el campo de entrada de texto después de enviar el mensaje
    }//GEN-LAST:event_sendButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                VistaChat vistaChat = new VistaChat(userName, cliente);
                vistaChat.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel chatroomTitle;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton logoutButton;
    private javax.swing.JTextField messageField;
    private javax.swing.JTextArea messagePanel;
    private javax.swing.JButton sendButton;
    // End of variables declaration//GEN-END:variables
}
