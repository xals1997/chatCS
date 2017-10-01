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
    PrintWriter out; //estoq eu ess?
    InterfazChat interfazchat;//Cuando se cree no dara error
    List<String> listaUsuarios = new ArrayList(); 
    JTextArea texto; //No se que es (Supongo que sera donde se introduce el texto)
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
    
    public void updateUsersList(String message){
        int start = message.indexOf("+");
        int end = message.lastIndexOf("+");
        int length = Integer.parseInt(message.substring((start + 1), end));
        String username = message.substring((end + 1), message.length());
        listaUsuarios.add(user);
        
        if(listaUsuarios.size()==length){
            DefaultListModel model = new DefaultListModel(); //wtf es esto loko??
           textoClientes.setModel(model);
            
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
           // interfazchat.enableDisconnected();
        }
    }
    
    public void sendMessage(String mensaje){
        out.println(mensaje);
        out.flush();//pero que mierdo es esto!!!!
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
               // interfazchat.enableDisconnected();
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
