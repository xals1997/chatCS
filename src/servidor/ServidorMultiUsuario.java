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

public class ServidorMultiUsuario implements Runnable{
    
    ServerSocket socketServidor;
    Socket socketNuevoCliente;
    static List<ClienteConectado> clientes = new ArrayList<>();
    static List<String> usuarioClientes = new ArrayList<>();
    static List<String> RSAlista = new ArrayList<>();
    int puerto;
    
    Thread hilo;
    
    public void creahilo(){
        hilo = new Thread(this);
        hilo.start();
    }
    
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
            String rsa = in.readLine();
            RSAlista.add(rsa);
            String nombreUsuario = in.readLine();
            nombreUsuario = nombreUsuario.toLowerCase();
    
            if (!usuarioClientes.contains(nombreUsuario)) {
                ClienteConectado newClient = new ClienteConectado(socketNuevoCliente, nombreUsuario);
                clientes.add(newClient);
                usuarioClientes.add(nombreUsuario);
                int length = clientes.size();

                
                System.out.println("esro seria la clve rsa, espero: "+rsa);
                System.out.println("En la lista hay "+RSAlista.size()+" clave/s guardadas");
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
    
    public void desconectarServidor(){
        try{
            socketServidor.close();
            socketNuevoCliente.close();
            clientes.clear();
            usuarioClientes.clear();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void run() {
        esperaClientes();
    }
}
