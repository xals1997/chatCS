package cliente;
/*
 * @author Alberto Berenguer
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.io.IOException;
import java.security.*;
import java.util.Base64;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
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
import javax.imageio.ImageIO;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class ChatCliente implements Runnable{
    int port;
    String host;
    String user;
    Thread thClient; //eto qe es?
    Socket socket;
    BufferedReader pasoClaveRSA;
    PrintWriter RSAjaja;
    BufferedReader in;
    PrintWriter out; //es cómo se escribe en un fichero de texto. Java tiene dos clases 
                     //para escribir en el, printWriter y printStream. printWriter recibe un objeto file
                     //en el constructor, puede lanzar excepcion FileNotFoundException, siempre try y catch
                     //https://www.youtube.com/watch?v=pmKwnZHb4wo
    InterfazChat interfazchat;//Cuando se cree no dara error
    List<String> listaUsuarios = new ArrayList(); 
    JTextArea texto; //Es un cuadro de texto
    JList textoClientes;//la lista de los conectados
    DefaultListModel model = new DefaultListModel(); 
    //varaibles para el paso de imagenes
    DataInputStream input;
    BufferedInputStream bis;
    BufferedOutputStream bos;
    int inn;
    byte[] byteArray;
     //Fichero a transferir
    String filename = "c:\\test.pdf"; //por ejemplo
    //Colocamos setters y gettes chavales
    
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
            
            
            pasoClaveRSA = new BufferedReader(new InputStreamReader (socket.getInputStream()));
            RSAjaja = new PrintWriter(socket.getOutputStream());
            
            
            
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            
            
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            RSAPublicKey publica = (RSAPublicKey) publicKey;
     
            System.out.println(publicKey);
            System.out.println();
            System.out.println("Modulo clave privada:   "+privateKey.getModulus());
            System.out.println("Exponente calve privada: "+privateKey.getPrivateExponent());
           
            //RSAjaja.println(privateKey.getPrivateExponent());
            out.println(publica.getModulus());
            
            waitAcceptance(); //esoera a que tal
            
            
            
         
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void generarAES(){
        try{
         KeyGenerator keyGen = KeyGenerator.getInstance("AES");
	      keyGen.init(128);
	     SecretKey clave = keyGen.generateKey();
	      System.out.println(Base64.getEncoder().encodeToString(clave.getEncoded()));
        }catch(Exception e){
            System.out.println("No genera la clave AES");
        }
    }
    //creal el hilo
    public void startThreadClient(){
        thClient = new Thread(this);
        thClient.start();
    }
    
   
    public void getMessages(){
        try{
            String mensaje;
            
            while((mensaje = in.readLine())!= null){
                System.out.print(mensaje);
                             
                if(mensaje.length()>1 && mensaje.substring(0, 1).equals("+")){
                   
                     updateUsersList(mensaje);                  
                
                }else{
                    texto.append(mensaje);
                   // texto.setCaretPosition(texto.getDocument().getLength());
                    texto.append("\n");
                }
                if(mensaje.equals("CrearAES")){                  
                         generarAES();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    //actualizacion de los usuarios en linea
    public void updateUsersList(String message){
        int start = message.indexOf("+");
        int end = message.lastIndexOf("+");
        int length = Integer.parseInt(message.substring((start + 1), end));
        String username = message.substring((end + 1), message.length());
        listaUsuarios.add(user);
        
        if(listaUsuarios.size()==length){
            //https://serprogramador.es/usando-un-jlist-para-almacenar-objetos-java-swing/
            //vale digamos que es una especie de modelo vacío, al que luego le añades cosas
            //constructor de la lista de los conectados, se le añade el modelo, vacío aún
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
        model.removeAllElements();
        listaUsuarios.remove(user);
        textoClientes.removeAll();
        listaUsuarios.clear();
        
    }
    public void getMessagesConnected(Boolean b, String text) throws IOException{
        if(!b){
            JOptionPane.showMessageDialog(null ,text);
            socket.close();
           interfazchat.enableDisconnected();
        }
    }
    
    public void sendMessage(String mensaje){
        out.println(mensaje);
        out.flush();//pero que mierdo es esto!!!! //hacer un flush, significa limpiar el buffer,
       /* Hace que todo lo que se ha escrito en System.out sea
completamente escrito. System.out puede hacer algún buffer interno de datos,
por lo que no está garantizado que algo escrito en el flujo
aparecerá inmediatamente en la salida estándar del proceso ().*/       
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
