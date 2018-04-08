package practica3_chatmulticast;

import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Servidor {
  public static void main(String [] args) {
    try {
      int pto = 1234;
      String dir = "229.1.1.1";
      MulticastSocket s = new MulticastSocket(1234);      
      InetAddress grupo = null;
      
      try {
        grupo = InetAddress.getByName(dir);
      } catch (UnknownHostException u) {u.printStackTrace();}
      
      s.setReuseAddress(true);
      s.setTimeToLive(255);
      
      s.joinGroup(grupo);
      System.out.println("Me uní al grupo. Empezaré a escuchar todo!");
      
      ArrayList<String> listaUsuarios = new ArrayList<>();
      
      for (;;) {
        DatagramPacket p = new DatagramPacket(new byte[65500], 65500);
        s.receive(p);
        
        sleep(50);
        
        String texto = new String(p.getData(), 0, p.getLength());  
        System.out.println("Se envió: " + texto);
        
        if (texto.contains("$")) {
          if (texto.contains("newUserAdd")) {
            String newUser = texto.split("@")[1];
            /* Primero envio lo que ya tengo */
            String mensaje = "$usersList@" + strUsuariosLista(listaUsuarios);
            System.out.println("Enviare: " + mensaje);
            
            byte[] b          = mensaje.getBytes();  // Conversión a bytes    
            DatagramPacket p2 = new DatagramPacket(b, b.length, grupo, pto);  // Creación del paquete
    
            try {
              s.send(p2);
            } catch (IOException ex) {}
                        
            /* Después agrego el nuevo usuario al ArrayList */
            listaUsuarios.add(newUser);
          }
        
        }
        
      }
      
    } catch (Exception e) {e.printStackTrace();}
    }
  
  public static String strUsuariosLista(ArrayList<String> listaUsuarios) {
    String usuarios = "";
    
    for (String usuario: listaUsuarios) {
      usuarios += usuario + ";";
    }
    
    System.out.println("Usuarios: " + usuarios);
    
    return usuarios;
  }
}
