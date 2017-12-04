
package servidor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.math.BigInteger;
import java.security.spec.RSAPublicKeySpec;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.NoSuchAlgorithmException;



public class ClienteConectado implements Runnable{
    
    Socket socket;
    String nombreUsuario;
    Thread hiloGetMensaje;
    BufferedReader in;
    PrintWriter out;
    RSAPublicKey publicKey;
    Boolean tieneAES = new Boolean(false);
    
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
     
     public void crearpublica(String modulo, String exponente) throws InvalidKeySpecException{
         
         try{
             
         
            BigInteger clave = new BigInteger(modulo,10); // hex base
            BigInteger claveexponente = new BigInteger(exponente,10); // decimal base

            RSAPublicKeySpec keySpeck = new RSAPublicKeySpec(clave, claveexponente);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpeck);
         } catch (NoSuchAlgorithmException ex) {
            System.out.println("AY CARAMBA DIJO VAR SINSON");
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
      
      public RSAPublicKey getPublica(){
          return(publicKey);
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
