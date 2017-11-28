package cliente;
/*
 * @author Alberto Berenguer
 */
import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
	


 import java.security.Key;

 import javax.crypto.Cipher;
 import javax.crypto.spec.SecretKeySpec;

 import sun.misc.BASE64Decoder;
 import sun.misc.BASE64Encoder;


public class ChatCliente implements Runnable{
    int port;
    String host;
    String user;
    Thread thClient; //eto qe es?
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    InterfazChat interfazchat;//Cuando se cree no dara error
    List<String> listaUsuarios = new ArrayList(); 
    JTextArea texto; //Es un cuadro de texto
    JList textoClientes;//la lista de los conectados
   
    //varaibles para el paso de imagenes
    DataInputStream input;
    BufferedInputStream bis;
    BufferedOutputStream bos;
    int inn;
    byte[] byteArray;
     //Fichero a transferir
    String filename = "c:\\test.pdf"; //por ejemplo
    //Colocamos setters y gettes chavales
    SecretKey clave_secreta;
    Cipher cifrador;
    public void setFilename(String file){
        this.filename = file;
    }
    public void setPort(int port){
        this.port = port;
    }
    public int getPort(){
        return port;
    }
    public void setHost(String host){
        this.host = host;
    }
    public String getHost(){
        return host;
    }
    
    public void setUser(String user){
        this.user = user;
    }
    public String getUser(){
        return user;
    }
    public void setTexto(JTextArea texto){
        this.texto = texto;
    }
    public void setTextClientes(JList textoClientes){
        this.textoClientes = textoClientes;
    }
    public void setInterfaz(InterfazChat interfazchat){
        this.interfazchat = interfazchat;
    }
    
   //Aqui ya hacemos mas cosas
    
    public void configCliente(){
        try{
            socket = new Socket(host, port);//creamos el socket
            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //esto se supone que lle lo del socket
            out = new PrintWriter(socket.getOutputStream());//esto envia el texto
            generarAES();
            waitAcceptance(); //esoera a que tal
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //creal el hilo
    public void startThreadClient(){
        thClient = new Thread(this);
        thClient.start();
        
    }
    
    public void generarAES(){
        KeyGenerator keygen;
        try {
            keygen = KeyGenerator.getInstance("AES");
            keygen.init(128);
            clave_secreta=keygen.generateKey();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ChatCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String cifrarContenido(String dato)throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException{
       
        
        
        
        cifrador = Cipher.getInstance("AES");
        cifrador.init(Cipher.ENCRYPT_MODE,clave_secreta);
        byte[] encriptado= cifrador.doFinal(dato.getBytes());
        String vuelve= new BASE64Encoder().encode(encriptado);
  
            return vuelve;
    
    }
    
        public String descifrarContenido(String dato)throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException{
        String vuelve ="";
            try{
        cifrador = Cipher.getInstance("AES");
        cifrador.init(Cipher.DECRYPT_MODE,clave_secreta);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(dato);
         byte[] decValue = cifrador.doFinal(decordedValue);//////////LINE 50
        vuelve = new String(decValue);
        return vuelve;
        }
          catch(IOException e){
            
        }
      return vuelve;
            
    
    }
   
    public void getMessages(){
        try{
  
           String mensaje;
            
            while((mensaje = in.readLine())!= null){               
                if(mensaje.length()>1 && mensaje.substring(0, 1).equals("+")){
                   updateUsersList(mensaje);
                }else{                   
                    texto.append(mensaje);
                    texto.append("\n");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
int cont=0;
    //actualizacion de los usuarios en linea
    public void updateUsersList(String message){
        
        int start = message.indexOf("+");
        int end = message.lastIndexOf("+");
        int length = Integer.parseInt(message.substring((start + 1), end));
        String username = message.substring((end + 1), message.length());
           
        listaUsuarios.add(username);
        
        if(listaUsuarios.size()==length){
            //https://serprogramador.es/usando-un-jlist-para-almacenar-objetos-java-swing/
            //vale digamos que es una especie de modelo vacío, al que luego le añades cosas
            //constructor de la lista de los conectados, se le añade el modelo, vacío aún
            DefaultListModel model = new DefaultListModel(); 
           textoClientes.setModel(model);
            
           //en este bucle se le van añadiendo al modelo, que ya está en textoClientes
           //los usuarios de la lista de Usuarios,
            for(String user : listaUsuarios){         
                model.addElement(user);             
            }
            
            listaUsuarios.clear();
            
        } 
    }
    public void limpia_lista(){
        /*model.removeAllElements();*/
        listaUsuarios.remove(user);
        /*textoClientes.removeAll();
        listaUsuarios.clear();*/
        
    }
    public void getMessagesConnected(Boolean b, String text) throws IOException{
        if(!b){
            JOptionPane.showMessageDialog(null ,text);
            socket.close();
           interfazchat.enableDisconnected();
           
        }
    }
    
    public void sendMessage(String mensaje){
        try {
           
            String mensajec = cifrarContenido(mensaje);
            /*System.out.print(mensaje);*/
            out.println(mensajec);
            out.flush();
         /*   decryptMessage(mensajec);*/
        }catch(Exception e){
           System.out.print("Algo pasa al cifrar: "+ e);
        }
    }
    
    public void decryptMessage(String mensaje){
                try {
            
            String mensajed = descifrarContenido(mensaje);
            /*System.out.print(mensajed);
            out.println("Desencriptando..."+mensajed);
            out.flush();*/
        }catch(Exception e){
           System.out.print("Algo pasa al desencriptar: "+ e);
        }
      
        
        
        
    }
    
    public void sendImage(String ruta_imagen){
        try{
            final File localFile = new File( ruta_imagen );
            bis = new BufferedInputStream(new FileInputStream(localFile));
            bos = new BufferedOutputStream(socket.getOutputStream());
            //Enviamos el nombre del fichero
            DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(localFile.getName());
            //Enviamos el fichero
            byteArray = new byte[8192];
            while((inn = bis.read(byteArray)) != -1){
               bos.write(byteArray,0,inn);
            }
 
        bis.close();
        bos.close();
        }catch ( Exception e ) {
            System.err.println(e);
         }
    }
    
    public void waitAcceptance(){
        try{
            out.println(user);
            out.flush();
            String message = in.readLine();
            if(!message.equals("true")){
                texto.append(message);
                texto.append("\n");
                socket.close();
               interfazchat.enableDisconnected();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //Cuando se enciente el chat
    public void run() {
        configCliente(); //abre el socket y crea las cosas
        getMessages();
    }
}
