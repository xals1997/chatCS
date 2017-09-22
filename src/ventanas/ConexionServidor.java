
package ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JTextField;

public class ConexionServidor implements ActionListener {
    private Socket socket;
    private JTextField mensaje;
    private String Usuario;
    private DataOutputStream salida;
    
    
    //Para los datos que recibimos del servidor
    public ConexionServidor(Socket socket,JTextField mensaje,String Usuario){
        this.socket=socket;
        this.mensaje=mensaje;
        this.Usuario=Usuario;
        
        try{
            this.salida= new DataOutputStream(socket.getOutputStream());
        }catch(IOException e){
          //No se puede crear el data output
        }catch (NullPointerException ex) {
            //No se puede crear el socket
        }
    }
    //Aqui se cifrará los mensajes y tal
    public static String cifradoClavePrivada(String mensaje){
           return mensaje+"cifrado";
    }
    
    //Para enviar un mensaje al hacer click
    public void ActionPerformed(ActionEvent e){
        try{
         salida.writeUTF(cifradoClavePrivada(Usuario+": "+ mensaje.getText()));
         mensaje.setText("");
        }catch(IOException ex){
            //Error al intentar enviar el mensaje
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
