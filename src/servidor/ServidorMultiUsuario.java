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
                System.out.println("El servidor espera");
                socketNuevoCliente = socketServidor.accept();
                System.out.println("Se ha conectado alguien");
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
            
            String key = in.readLine();
            String expKey = in.readLine();
           
            System.out.println("El modulo: "+key);
            System.out.println("El exponente: "+expKey);
            String nombreUsuario = in.readLine();
            nombreUsuario = nombreUsuario.toLowerCase();
    
            if (!usuarioClientes.contains(nombreUsuario)) {
                
               
                ClienteConectado newClient = new ClienteConectado(socketNuevoCliente, nombreUsuario);
                clientes.add(newClient);
                newClient.crearpublica(key, expKey);
              
                
                usuarioClientes.add(nombreUsuario);
                int length = clientes.size();

                out.println("true");
                out.flush();
                  if(clientes.size()==1){
                     out.println("rtt/q2_ generarAES");
                     newClient.tieneAES = true;
                     out.flush();
                }
                  else
                  {
                      for(ClienteConectado client : clientes)
                      {
                          System.out.println(client.tieneAES);
                          if(client.tieneAES)
                          {
                              client.enviarMensaje("rtt/q2_ encriptaAES "+key+" "+expKey +" "+newClient.nombreUsuario);
                              break;
                          }
                      }
                  }
                 
                for (ClienteConectado client : clientes) {
                    client.enviarMensaje("rtt/q2_ El usuario '" + nombreUsuario + "' ha entrado al chat.");
                    

                    for (ClienteConectado cc : clientes) {
                        client.enviarMensaje("+" + length + "+" + cc.nombreUsuario);
                    }
                }
            } else {
                out.println("rtt/q2_ No ha sido posible conectarse'" + nombreUsuario + "' ya está en uso.");
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
            }
    }
    
    public void desconectarServidor(){
        try{
            socketServidor.close();
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
