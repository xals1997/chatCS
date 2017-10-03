
package servidor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteConectado implements Runnable{
    
    Socket socket;
    String nombreUsuario;
    Thread hiloGetMensaje;
    BufferedReader in;
    PrintWriter out;
    
     public ClienteConectado(Socket socket, String usuario) {
        configurarCliente(socket);
        this.nombreUsuario = usuario;
    }
     public void configurarCliente(Socket socket){
         this.socket = socket;
         
         try{
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            hiloGetMensaje = new Thread(this);
            hiloGetMensaje.start();
        }catch (Exception e) {
            e.printStackTrace();
        }
     }
      public void getMensajes() {
        try{
            String mensaje;
            while ((mensaje = in.readLine()) != null) {

                for (ClienteConectado cliente : ServidorMultiUsuario.clientes) {
                    cliente.enviarMensaje(mensaje);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
      
      public void enviarMensaje(String mensaje) {
        out.println(mensaje);
        out.flush();
    }
      @Override 
    public void run() {
        getMensajes();
    }
}
