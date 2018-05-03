package graphic;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;

public class Recibir extends Thread {
    
    HTMLpane mensajeria;
    String hilo;
    MulticastSocket msR;
    InetAddress gpo;
    int ptR;
    JComboBox Friends;
    JEditorPane model;
    String Usuario;
    ArrayList<String> nombres = new ArrayList<>();
    boolean priOrpub = false;
    boolean terminar = false;
    
    Recibir(String msg, HTMLpane mensajeria, String Usuario, MulticastSocket msR, InetAddress gpo, int ptR, JComboBox Friends) {
        super(msg);
        this.hilo = msg;
        this.mensajeria = mensajeria;
        this.Usuario = Usuario;
        this.msR = msR;
        this.gpo = gpo;
        this.ptR = ptR;
        this.Friends = Friends;
        this.priOrpub = false;
    }
    
    Recibir(String msg, HTMLpane mensajeria, String Usuario, MulticastSocket msR, InetAddress gpo, int ptR, JEditorPane model) {
        super(msg);
        this.hilo = msg;
        this.mensajeria = mensajeria;
        this.Usuario = Usuario;
        this.msR = msR;
        this.gpo = gpo;
        this.ptR = ptR;
        this.model = model;
        this.priOrpub = false;
    }
    
    Recibir(String msg, HTMLpane mensajeria, String Usuario, MulticastSocket msR, InetAddress gpo, int ptR) {
        super(msg);
        this.hilo = msg;
        this.mensajeria = mensajeria;
        this.msR = msR;
        this.gpo = gpo;
        this.ptR = ptR;
        this.Usuario = Usuario;
        this.priOrpub = true;
    }
    
    @Override
    public void run() {
        try {
            for (;;) {
                while (this.terminar == false) {
                    recibir();
                    Thread.sleep(100);
                }
                
            }
        } catch (Exception e) {
        }
    }
    
    public void recibir() {
        try {
            
            System.out.println("recibiendo");
            DatagramPacket p = new DatagramPacket(new byte[65535], 65535);
            msR.receive(p);
            ObjectInputStream oiss = new ObjectInputStream(new ByteArrayInputStream(p.getData()));
            Mensaje men = (Mensaje) oiss.readObject();
            
            System.out.println("-----------------");
            System.out.println("hilo: " + this.hilo);
            System.out.println("Usuario: " + this.Usuario);
            System.out.println("priOrpub: " + this.priOrpub);
            System.out.println("M_Usuario: " + men.getUsuario());
            System.out.println("M_Destinatario: " + men.getDestinatario());
            System.out.println("M_Mensaje: " + men.getMensaje());
            
            if (men.getMensaje().length() != 0) {
//                    this.mensajeria.append(men.getMensaje(),men.getHora(),men.getUsuario(),this.Usuario.equals(men.getUsuario()));
            }
            
            if (men.getTipo().equalsIgnoreCase("cerrar") && this.priOrpub == true) {
                this.terminar = true;
                
                Calendar calendario = Calendar.getInstance();
                int horaH = calendario.get(Calendar.HOUR_OF_DAY);
                int min = calendario.get(Calendar.MINUTE);
                int sec = calendario.get(Calendar.SECOND);
                String hora = String.valueOf(horaH + ":" + min + ":" + sec);
                String mensaje = "";
                System.out.println("ENTRO A CERRAR");
                Mensaje M = new Mensaje(Usuario, hora, mensaje, men.getUsuario(), "cerrar");
                for (int i = 0; i < 2; i++) {
                    Thread enviar = new Enviar("Enviar", mensajeria, msR, gpo, ptR, M);
                    enviar.start();
                }
                
            }
            
            if (men.getDestinatario().equalsIgnoreCase(this.Usuario) && men.getTipo().equalsIgnoreCase("conectar")) {
                System.out.println("Entro conectar");
                System.out.println("-----------------");
                System.out.println("Usuario: " + this.Usuario);
                System.out.println("Destinatario: " + men.getUsuario());
                
                ChatPrivado_Frame CP = new ChatPrivado_Frame(men.getUsuario(), this.Usuario, msR, gpo, ptR);
                CP.setVisible(true);
                
            }
            if ((this.Usuario.equalsIgnoreCase(men.getDestinatario()) || this.Usuario.equalsIgnoreCase(men.getUsuario())) && priOrpub == true && men.getTipo().equalsIgnoreCase("privado")) {
                System.out.println("privado");
                System.out.println("-----------------");
                if (men.getMensaje().length() != 0) {
                    this.mensajeria.append(men.getMensaje(), men.getHora(), men.getUsuario(), this.Usuario.equals(men.getUsuario()));
                }
                
            }
            if (men.getTipo().equalsIgnoreCase("publico") && priOrpub == false) {
                System.out.println("Entro publico");
                System.out.println("-----------------");
                if (men.getMensaje().length() != 0) {
                    System.out.println("agrego al html");
                    this.mensajeria.append(men.getMensaje(), men.getHora(), men.getUsuario(), this.Usuario.equals(men.getUsuario()));
                }
                if (!this.Usuario.equals(men.getUsuario())) {
                    if (estaEn(men.Usuario)) {
                        this.nombres.add(men.Usuario);
                        
                        this.Friends.addItem(men.Usuario);
                        
                    }
                }
                
            }
            
        } catch (IOException | ClassNotFoundException e) {
        }
    }
    
    public boolean estaEn(String user) {
        for (int j = 0; j < nombres.size(); j++) {
            if (nombres.get(j).equals(user) || Friends.getModel().getElementAt(j).equals(user)) {
                return false;
            }
        }
        
        return true;
    }
    
}
