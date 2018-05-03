package graphic;

import static graphic.ChatFrame.Usuario;
import java.awt.Image;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Calendar;
import javax.swing.ImageIcon;

public class ChatPrivado_Frame extends javax.swing.JFrame{
    String UsuarioDestinatario;
    String Usuario;
    MulticastSocket ms;
    InetAddress gpo;
    int pt;
    Thread recibir;
    emojis em;
    
    public ChatPrivado_Frame(String UsuarioDestinatario, String Usuario, MulticastSocket ms, InetAddress gpo, int pt){
        initComponents();
        //em = new emojis(this.getBounds(),Atext);
//        html.seticons(em.getimages());
        
        this.btnSend.setText("<html><p>Enviar &#8663;</p></html>");
        //ImageIcon img = new ImageIcon("emojis/sonrisa.png");
        //jButton3.setIcon(new ImageIcon(img.getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT)));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
        this.UsuarioDestinatario = UsuarioDestinatario;
        this.Usuario = Usuario;
        Usuario_Field.setText(Usuario);
        UsuarioD_Field.setText(UsuarioDestinatario);
        this.ms = ms;
        this.gpo = gpo;
        this.pt = pt;
        //em = new emojis(this.getBounds(),jTextField1);
        
        recibir();
    }
    
    public ChatPrivado_Frame() {
        initComponents();
    }
    
    public void recibir(){
        recibir = new Recibir("RecibirPrivado",html,Usuario,ms,gpo,pt);
        recibir.start();
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        btnSend = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        html = new HTMLpane();
        jScrollPane1 = new javax.swing.JScrollPane();
        Atext = new javax.swing.JTextArea();
        Usuario_Field = new javax.swing.JLabel();
        UsuarioD_Field = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel3.setFont(new java.awt.Font("Roboto Mono", 0, 14)); // NOI18N
        jLabel3.setText("<-------------->");

        btnSend.setText("Send");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        jButton2.setText("Cerrar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(html);

        Atext.setColumns(20);
        Atext.setRows(5);
        Atext.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                AtextKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(Atext);

        Usuario_Field.setFont(new java.awt.Font("Roboto Mono", 0, 14)); // NOI18N
        Usuario_Field.setText("jLabel1");

        UsuarioD_Field.setFont(new java.awt.Font("Roboto Mono", 0, 14)); // NOI18N
        UsuarioD_Field.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                        .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(Usuario_Field)
                        .addGap(50, 50, 50)
                        .addComponent(jLabel3)
                        .addGap(34, 34, 34)
                        .addComponent(UsuarioD_Field)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jButton2)
                    .addComponent(Usuario_Field)
                    .addComponent(UsuarioD_Field))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSend)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        if(!Atext.getText().equals("")){
            Calendar calendario = Calendar.getInstance();
            int horaH =calendario.get(Calendar.HOUR_OF_DAY);
            int min = calendario.get(Calendar.MINUTE);
            int sec = calendario.get(Calendar.SECOND);
            String hora = String.valueOf(horaH+":"+min+":"+sec);
            String mensaje = Atext.getText();

            Mensaje M = new Mensaje(Usuario,hora,mensaje,UsuarioDestinatario,"privado");

            for(int i=0;i<3;i++){
                Thread enviar = new Enviar("Enviar",html,ms,gpo,pt,M);
                enviar.start();
            }
            
            Atext.setText("");
            
        }
        
    }//GEN-LAST:event_btnSendActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Calendar calendario = Calendar.getInstance();
        int horaH =calendario.get(Calendar.HOUR_OF_DAY);
        int min = calendario.get(Calendar.MINUTE);
        int sec = calendario.get(Calendar.SECOND);
        String hora = String.valueOf(horaH+":"+min+":"+sec);
        String mensaje = "";

        Mensaje M = new Mensaje(Usuario,hora,mensaje,UsuarioDestinatario,"cerrar");
        for(int i=0;i<2;i++){
            Thread enviar = new Enviar("Enviar",html,ms,gpo,pt,M);
            enviar.start();
        }

        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void AtextKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AtextKeyTyped
        
        Atext.setText(html.remplaceemo(Atext.getText()));
        if(evt.getKeyChar()=='\n'){
           btnSend.doClick();
       }
    }//GEN-LAST:event_AtextKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea Atext;
    private javax.swing.JLabel UsuarioD_Field;
    private javax.swing.JLabel Usuario_Field;
    private javax.swing.JButton btnSend;
    /*
    private javax.swing.JTextPane html;
    */
    HTMLpane html;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables

    
}
