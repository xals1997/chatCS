package cliente;
/*
 * @author Alberto Berenguer
 */
//import com.sun.org.apache.xml.internal.security.utils.Base64;
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
import java.math.BigInteger;
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
import java.security.interfaces.RSAPublicKey;
	


import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

 import javax.crypto.Cipher;
 import javax.crypto.spec.SecretKeySpec;

 import sun.misc.BASE64Decoder;
 import sun.misc.BASE64Encoder;
 import java.util.Base64;

public class ChatCliente implements Runnable{
    
    
    KeyPairGenerator keyPairGenerator;
    KeyPair keyPair;
    RSAPrivateKey privateKey;
    RSAPublicKey publicKey;
    
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
            generaryenviarRSA();
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
    public String descifrarRSA( String claveCifrada) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
        System.out.println("en teoria es : "+claveCifrada);
     String textoDesencriptado="";  
     byte[] bytesDesencriptados;
        try {
              
            Cipher rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            rsa.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] claveBytes = new BASE64Decoder().decodeBuffer(claveCifrada);
            bytesDesencriptados = rsa.doFinal(claveBytes);
            textoDesencriptado = new String(bytesDesencriptados);
            
        } catch (IOException ex) {
            Logger.getLogger(ChatCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return textoDesencriptado;
    }
    public void generarAES(){
        KeyGenerator keygen;
        try {
            keygen = KeyGenerator.getInstance("AES");
            keygen.init(128);
            clave_secreta=keygen.generateKey();
            System.out.println("Clave AES generada");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ChatCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void generaryenviarRSA(){
        
        try{
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPair = keyPairGenerator.generateKeyPair();
            
            
            
            
            privateKey = (RSAPrivateKey) keyPair.getPrivate();
            publicKey = (RSAPublicKey) keyPair.getPublic();
            
            
            //System.out.println("Modulo clave patata:   "+publicKey.getModulus());
            //System.out.println("Exponente calve publica: "+publicKey.getPublicExponent());
           
            out.println(publicKey.getModulus());
            out.println(publicKey.getPublicExponent());
        } catch (NoSuchAlgorithmException ex) {
           System.out.println("AY CARAMBA DIJO VAR SINSON");
        }
        
    }
     public void cifraAES(String modulo, String exponente, String tete) throws InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
     {
         try{

            String aesString = Base64.getEncoder().encodeToString(clave_secreta.getEncoded());
            BigInteger clave = new BigInteger(modulo,10); // hex base
            BigInteger claveexponente = new BigInteger(exponente,10); // decimal base

            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(clave, claveexponente);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKey rsa = (RSAPublicKey) keyFactory.generatePublic(keySpec);
            //System.out.println("La clave se ha reconstruido correctamente");
            
            cifrador = Cipher.getInstance("RSA");
            cifrador.init(Cipher.ENCRYPT_MODE,publicKey);
            byte[] encriptado= cifrador.doFinal(aesString.getBytes());
            
            String stringEncriptado="";
            
            
            /*for(int i=0; i<encriptado.length; i++){
                
                stringEncriptado+=encriptado[i];
                
            }*/
            for (byte b : encriptado) {
            stringEncriptado += Integer.toHexString(0xFF & b);
            }
            
            out.println("rtt/q2_ AEScifrada " + tete + " " + stringEncriptado);
            out.flush();
            
            System.out.println("La clave AES ha sido cifrada - " + tete +" " +stringEncriptado);
            
         } catch ( Exception e) {
            e.printStackTrace();
        }
     }
    
    public String cifrarContenido(String dato)throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException{
       String vuelve="";
        try{
        cifrador = Cipher.getInstance("AES");
        cifrador.init(Cipher.ENCRYPT_MODE,clave_secreta);
        byte[] encriptado= cifrador.doFinal(dato.getBytes());
        vuelve= new BASE64Encoder().encode(encriptado); //a encode, se le pasa un array de bytes
    
        }catch(Exception e){}
            return vuelve;
    
    }
    
        public String descifrarContenido(String dato)throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException{
        
       System.out.println("--- descifrarContenido --- ");     
            
       String hola="";
       String[] aux=dato.split(" ");
       System.out.println("aux[0]"+ aux[0]);
       if(aux[0].compareToIgnoreCase("rtt/q2_")!=0){
                byte[] decValue=null;
            try{
                cifrador = Cipher.getInstance("AES");
                cifrador.init(Cipher.DECRYPT_MODE,clave_secreta);

            byte[] decordedValue = new BASE64Decoder().decodeBuffer(dato); //a decodeBuffer se le pasa un String, y no un array de bytes 

             decValue = cifrador.doFinal(decordedValue);//////////LINE 50

             hola =new String(decValue);
            }
              catch(IOException e){
                    //System.out.println(e.getStackTrace());
                    e.printStackTrace();
            }
       }
       else{
           
            if(aux[1].toUpperCase().compareTo("generarAES".toUpperCase())==0){
                    generarAES();
                    hola = "";
            }
            if(aux[1].toUpperCase().compareTo("encriptaAES".toUpperCase())==0)
            {
                cifraAES(aux[2], aux[3],aux[4]);
                hola="";
          
            }
            else{
                if(aux[1].toUpperCase().compareTo("AEScifrada".toUpperCase())==0){                   
                    if(aux[2].toUpperCase().compareToIgnoreCase(user.toString())==0){
                       
                        hola = descifrarRSA(aux[3]);
                    // decode the base64 encoded string
                    
                        System.out.print(hola);
                        byte[] decodedKey = Base64.getDecoder().decode(hola);
                        // rebuild key using SecretKeySpec
                        clave_secreta = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES"); 


                    }
                }
       
                for(int i=1;i<aux.length;i++){
                       hola+=aux[i]+" ";
                   }
               
          
       }
       
        
    }
            return hola;
        }
        

   
    public void getMessages(){
        try{
  
           String mensaje;   
            while((mensaje = in.readLine())!= null){   
               
               
                if(mensaje.length()>1 && mensaje.substring(0, 1).equals("+")){
                   updateUsersList(mensaje);
                }else{
                    mensaje= decryptMessage(mensaje);
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
       try{ int length = Integer.parseInt(message.substring((start + 1), end));
       
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
       catch(NumberFormatException e){}
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
        
        }catch(Exception e){
           System.out.print("Algo pasa al cifrar: "+ e);
        }
    }
    
    public String decryptMessage(String mensaje){
              String mensajed="";
              
        
        try {
            mensajed = descifrarContenido(mensaje);
            /*System.out.print(mensajed);
            out.println("Desencriptando..."+mensajed);
            out.flush();*/
        }catch(Exception e){
           System.out.print("Algo pasa al desencriptar: "+ e);
        }
      
        
        return mensajed;
        
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
                
                texto.append(descifrarContenido(message));
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
        try{
        configCliente(); //abre el socket y crea las cosas
        getMessages();
        }
        catch(NumberFormatException e){
            
        }
    }
}
