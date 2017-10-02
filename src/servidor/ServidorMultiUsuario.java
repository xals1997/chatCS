/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import cliente.ChatCliente;
import cliente.InterfazChat;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class ServidorMultiUsuario {
    
    ServerSocket socketServidor;
    Socket socketNuevoCliente;
    static List<ClienteConectado> clientes = new ArrayList<>();
    static List<String> usuarioClientes = new ArrayList<>();
    Scanner tecladoIn = new Scanner(System.in);
    int puerto;
    
    public void configurarServidor(int puerto){
        
        try{
            this.puerto = puerto;
            socketServidor = new ServerSocket(puerto);
        }catch (Exception e){
            e.printStackTrace();
        }
        
    }
    public void esperaClientes(){
         try{
            while(true) {
                socketNuevoCliente = socketServidor.accept();
                comprobarUsuario(socketNuevoCliente);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void comprobarUsuario(Socket socketNuevoCliente){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socketNuevoCliente.getInputStream()));
            PrintWriter out = new PrintWriter(socketNuevoCliente.getOutputStream());
            String nombreUsuario = in.readLine();
            nombreUsuario = nombreUsuario.toLowerCase();

            if (!usuarioClientes.contains(nombreUsuario)) {
                ClienteConectado newClient = new ClienteConectado(socketNuevoCliente, nombreUsuario);
                clientes.add(newClient);
                usuarioClientes.add(nombreUsuario);
                int length = clientes.size();

                out.println("true");
                out.flush();
                for (ClienteConectado client : clientes) {
                    client.enviarMensaje("El usuario '" + nombreUsuario + "' ha entrado al chat.");

                    for (ClienteConectado cc : clientes) {
                        client.enviarMensaje("+" + length + "+" + cc.nombreUsuario);
                    }
                }
            } else {
                out.println("No ha sido posible conectarse'" + nombreUsuario + "' ya está en uso.");
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
}
    }
}
