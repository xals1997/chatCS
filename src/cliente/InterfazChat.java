/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

/**
 *
 * @author Alberto Berenguer
 */
public class InterfazChat extends javax.swing.JFrame {
    ChatCliente chat;
    /**
     * Creates new form InterfazChat
     */
    public InterfazChat() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bDesconectar = new javax.swing.JButton();
        fondo = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lista = new javax.swing.JList<>();
        emensaje = new javax.swing.JTextField();
        bfoto = new javax.swing.JButton();
        benviar = new javax.swing.JButton();
        tusuario = new javax.swing.JLabel();
        tservidor = new javax.swing.JLabel();
        tpuerto = new javax.swing.JLabel();
        epuerto = new javax.swing.JTextField();
        eusuario = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        areaMensajes = new javax.swing.JTextArea();
        bConectar = new javax.swing.JButton();
        baudio = new javax.swing.JButton();
        eservidor = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CHAT CS");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bDesconectar.setBackground(new java.awt.Color(255, 93, 93));
        bDesconectar.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        bDesconectar.setText("Desconectar");
        bDesconectar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        bDesconectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDesconectarActionPerformed(evt);
            }
        });
        getContentPane().add(bDesconectar, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 20, 90, 30));

        fondo.setBackground(new java.awt.Color(56, 178, 234));
        fondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBorder(null);

        lista.setBackground(new java.awt.Color(154, 250, 139));
        lista.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lista.setFont(new java.awt.Font("Berlin Sans FB", 0, 16)); // NOI18N
        lista.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lista.setOpaque(false);
        jScrollPane1.setViewportView(lista);

        fondo.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 130, 200));

        emensaje.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        emensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emensajeActionPerformed(evt);
            }
        });
        fondo.add(emensaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(269, 400, 520, 50));

        bfoto.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        bfoto.setText("Foto");
        bfoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bfotoActionPerformed(evt);
            }
        });
        fondo.add(bfoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 400, 70, -1));

        benviar.setBackground(new java.awt.Color(141, 210, 244));
        benviar.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        benviar.setText("Enviar");
        benviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                benviarActionPerformed(evt);
            }
        });
        fondo.add(benviar, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 400, 80, 50));

        tusuario.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        tusuario.setText("Usuario :");
        fondo.add(tusuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, -1, -1));

        tservidor.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        tservidor.setText("IP :");
        fondo.add(tservidor, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 20, -1, -1));

        tpuerto.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        tpuerto.setText("Puerto :");
        fondo.add(tpuerto, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 20, -1, -1));

        epuerto.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        fondo.add(epuerto, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 20, 71, -1));

        eusuario.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        eusuario.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.black, java.awt.Color.blue, null, null));
        eusuario.setCaretColor(new java.awt.Color(0, 0, 204));
        eusuario.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        eusuario.setDisabledTextColor(new java.awt.Color(0, 0, 102));
        fondo.add(eusuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 20, 122, -1));

        jLabel1.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel1.setText("En línea:");
        fondo.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 90, -1));

        jScrollPane2.setAutoscrolls(true);

        areaMensajes.setEditable(false);
        areaMensajes.setColumns(20);
        areaMensajes.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        areaMensajes.setRows(5);
        jScrollPane2.setViewportView(areaMensajes);

        fondo.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 60, 790, 330));

        bConectar.setBackground(new java.awt.Color(154, 250, 139));
        bConectar.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        bConectar.setText("Conectar");
        bConectar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        bConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bConectarActionPerformed(evt);
            }
        });
        fondo.add(bConectar, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 20, 90, 30));

        baudio.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        baudio.setText("Audio");
        fondo.add(baudio, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 430, 70, -1));

        eservidor.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        fondo.add(eservidor, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 20, 120, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/customer-service-icons-chat-blue.png"))); // NOI18N
        fondo.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 100, 90));

        jLabel2.setFont(new java.awt.Font("Berlin Sans FB", 0, 11)); // NOI18N
        jLabel2.setText("Autores:");
        fondo.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, -1, -1));

        jLabel4.setFont(new java.awt.Font("Berlin Sans FB", 0, 11)); // NOI18N
        jLabel4.setText("Alejandro Domenech");
        fondo.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 380, -1, -1));

        jLabel5.setFont(new java.awt.Font("Berlin Sans FB", 0, 11)); // NOI18N
        jLabel5.setText("Alberto Berenguer");
        fondo.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 400, -1, -1));

        jLabel8.setFont(new java.awt.Font("Berlin Sans FB", 0, 11)); // NOI18N
        jLabel8.setText("Blanca Vázquez");
        fondo.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 440, -1, -1));

        jLabel9.setFont(new java.awt.Font("Berlin Sans FB", 0, 11)); // NOI18N
        jLabel9.setText("Carlos Aracil");
        fondo.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, -1, -1));

        getContentPane().add(fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1020, 470));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void emensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emensajeActionPerformed
        // TODO add your handling code here:
        if(emensaje.getText().equals("")){
            //no hace nada
        }else{
            String recojo=eusuario.getText()+": "+emensaje.getText()+"\n";
            chat.sendMessage(recojo);
             emensaje.setText("");
        }
    }//GEN-LAST:event_emensajeActionPerformed

    private void bConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bConectarActionPerformed
        
        if(eusuario.getText().equals("") || eservidor.getText().equals("") || epuerto.getText().equals("")){
            // no hace nada
        }else{
             int port = Integer.parseInt(epuerto.getText());
            String host = eservidor.getText();
            String username = eusuario.getText();
            chat = new ChatCliente();
            chat.setInterfaz(this);
             chat.setHost(host);
             chat.setPort(port);
             chat.setUser(username);
             chat.setTexto(areaMensajes);
             chat.setTextClientes(lista);        
             chat.startThreadClient();
            enableConnected();
        }
        
        
        
        
        
    }//GEN-LAST:event_bConectarActionPerformed

    private void bDesconectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDesconectarActionPerformed
        try {
            chat.socket.close();
            enableDisconnected();
        } catch (Exception e) {
            e.printStackTrace();
        }
        enableDisconnected();
    }//GEN-LAST:event_bDesconectarActionPerformed

    private void bfotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bfotoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bfotoActionPerformed

    private void benviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_benviarActionPerformed
        if(emensaje.getText().equals("")){
            //no hace nada
        }else{
            String recojo=eusuario.getText()+": "+emensaje.getText()+"\n";
            chat.sendMessage(recojo);
             emensaje.setText("");
        }
    }//GEN-LAST:event_benviarActionPerformed

    /**
     * @param args the command line arguments
     */
    public void enableConnected() {
        eservidor.setEditable(false);
        eservidor.setEnabled(false);
        epuerto.setEditable(false);
        epuerto.setEnabled(false);
        eusuario.setEditable(false);
        eusuario.setEnabled(false);
        emensaje.setEnabled(true);
        benviar.setEnabled(true);
        bConectar.setEnabled(false);
        bDesconectar.setEnabled(true);
    }

    public void enableDisconnected() {
        eservidor.setEditable(true);
        eservidor.setEnabled(true);
        epuerto.setEditable(true);
        epuerto.setEnabled(true);
        eusuario.setEditable(true);
        eusuario.setEnabled(true);
        emensaje.setEnabled(false);
        benviar.setEnabled(false);
        bConectar.setEnabled(true);
        bDesconectar.setEnabled(false);
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InterfazChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfazChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfazChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfazChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfazChat().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaMensajes;
    private javax.swing.JButton bConectar;
    private javax.swing.JButton bDesconectar;
    private javax.swing.JButton baudio;
    private javax.swing.JButton benviar;
    private javax.swing.JButton bfoto;
    private javax.swing.JTextField emensaje;
    private javax.swing.JTextField epuerto;
    private javax.swing.JTextField eservidor;
    private javax.swing.JTextField eusuario;
    private javax.swing.JPanel fondo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> lista;
    private javax.swing.JLabel tpuerto;
    private javax.swing.JLabel tservidor;
    private javax.swing.JLabel tusuario;
    // End of variables declaration//GEN-END:variables
}
