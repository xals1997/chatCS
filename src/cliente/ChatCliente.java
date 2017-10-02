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

public class ChatCliente implements Runnable{
    int port;
    String host;
    String user;
    Thread thClient; //eto qe es?
    Socket socket;
    BufferedReader in;
    PrintWriter out; //es cómo se escribe en un fichero de texto. Java tiene dos clases 
                     //para escribir en el, printWriter y printStream. printWriter recibe un objeto file
                     //en el constructor, puede lanzar excepcion FileNotFoundException, siempre try y catch
                     //https://www.youtube.com/watch?v=pmKwnZHb4wo
    InterfazChat interfazchat;//Cuando se cree no dara error
    List<String> listaUsuarios = new ArrayList(); 
    JTextArea texto; //Es un cuadro de texto
    JList textoClientes;//la lista de los conectados
    
    //Colocamos setters y gettes chavales
    
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
    
    //aqui actualiza lo de la lista de usuarios 
    public void getMessages(){
        try{
            String mensaje;
            while((mensaje = in.readLine())!= null){
                if(mensaje.substring(0, 1).equals("+")){
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
            //
            DefaultListModel model = new DefaultListModel(); //wtf es esto loko??
            
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
