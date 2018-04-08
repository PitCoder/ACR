package practica3_chatmulticast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import javax.swing.DefaultListModel;
import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.SwingWorker;

public class RecibeMensaje_Worker {
  private VentanaCliente  ventana;
  private MulticastSocket s;
  private JEditorPane     areaChat;
  private JScrollBar      scroll;
  private JList           usuarios;
  private String          myUser;
  private boolean         flagList;
  
  public RecibeMensaje_Worker(VentanaCliente ventana) {
    this.ventana  = ventana;
    areaChat      = ventana.getAreaChat();
    scroll        = ventana.getScrollBar();
    usuarios      = ventana.getListUsuarios();
    myUser        = ventana.getMyUser();
    flagList      = false;
    
    inicializarSocket();
  }
  
  /*
    Mensajes que se recibirán:
      - nombreUsuario@mensaje     Mensaje grupal.
      - $PMrem;dest@mensaje       Mensaje privado.
      - $newUserAdd@newUser       Se agregará un nuevo usuario.
      - $usersList@users
  */
  
  public void escuchar() {
    final SwingWorker worker = new SwingWorker() {
      @Override
      protected Object doInBackground() throws Exception {        
        while (true) {
          DatagramPacket p = new DatagramPacket(new byte[65500], 65500);
          s.receive(p);       
          
          String texto    = new String(p.getData(), 0, p.getLength());             

          System.out.println("Texto: " + texto);
          
          if (!texto.contains("$")) {                                           // Es un mensaje grupal
            System.out.println("Recibi mensaje grupal");
            agregaMensajeGrupal(texto);
          } else {            
            if (texto.contains("newUserAdd")) {              
              newUserAdd(texto);  // Acciones correspondientes p/agregar usuario.             
            } else if (texto.contains("PM")) {
              boolean f = esParaMi(texto);  // Se verifica que sea yo el destinatario
              
              if (f)  // Debo de imprimir el mensaje
                agregaMensajePersonal(texto);
              
              boolean f2 = yoLoEnvie(texto);
              
              if (f2)
                agregaMensajeEnviadoPersonal(texto);
              
            } else if (texto.contains("usersList") && !flagList) {              
              String ps = texto.substring(texto.indexOf("@"));
              flagList  = !flagList;
              
              if (ps.length() > 1)
                listaUsuarios(texto.split("@")[1]);
                            
            }
          } 
          
          areaChat.revalidate();
          scroll.setValue(scroll.getMaximum());

        }                
      }
    };
    
    worker.execute();
  }
  
  private void inicializarSocket() {
    try {
      String dir = "229.1.1.1";
      s = new MulticastSocket(1234);      
      InetAddress grupo = null;
      
      try {
        grupo = InetAddress.getByName(dir);
      } catch (UnknownHostException u) {u.printStackTrace();}
      
      s.joinGroup(grupo);
      System.out.println("Ya estoy escuchando!");
    } catch (IOException e) {}
  }    

  private String getContenido(String contenidoAnterior) {
    String contenido = contenidoAnterior.substring(contenidoAnterior.indexOf("<body>"), 
            contenidoAnterior.indexOf("</body>"));
    contenido = contenido.substring("<body>".length()).trim();
    
    return contenido;
  }

  private void newUserAdd(String texto) {
    DefaultListModel modelUsuarios = (DefaultListModel) usuarios.getModel();
    modelUsuarios.addElement(texto.split("@")[1]);

    usuarios.setModel(modelUsuarios);
  }
   
  private void agregaMensajeGrupal(String texto) {
    String[] partes = texto.split("@");
    String textoAnterior = getContenido(areaChat.getText());
    String text = textoAnterior + "<p> <b>" + partes[0] + ": </b> " + partes[1] + "</p>";
    areaChat.setText(text);  
  }
  
  private void agregaMensajePersonal(String texto) {
    String[] partes = texto.split("@");
    String textoAnterior = getContenido(areaChat.getText());
    String rem = partes[0].substring(partes[0].indexOf("$PM") +  3, partes[0].indexOf(";"));
    
    String text = textoAnterior + "<p> <b style='color: red';>" + rem + ": </b> " + partes[1] + "</p>";
    areaChat.setText(text);  
  }
  
  private void agregaMensajeEnviadoPersonal(String texto) {
    String[] partes = texto.split("@");
    String textoAnterior = getContenido(areaChat.getText());
    String rem = partes[0].substring(partes[0].indexOf("$PM") +  3, partes[0].indexOf(";"));
    String des = texto.substring(texto.indexOf(";") + 1, texto.indexOf("@"));
    
    String text = textoAnterior + "<p> <b style='color: blue';>" + rem + " -> " + des + ": </b> " + partes[1] + "</p>";
    areaChat.setText(text);  
  }
  
  private boolean esParaMi(String texto) {
    String dest = texto.substring(texto.indexOf(";") + 1, texto.indexOf("@"));
    
    return dest.compareTo(myUser) == 0;
  }
  
  private boolean yoLoEnvie(String texto) {
    String ori = texto.substring(texto.indexOf("$PM") + 3, texto.indexOf(";"));
    
    return ori.compareTo(myUser) == 0;
  }
  
  private void listaUsuarios(String usuariosStr) { 
    System.out.println("listUsuarios: " + usuariosStr);
    if (usuariosStr.contains(";")) {
      String[] usrL = usuariosStr.split(";");

      DefaultListModel modelUsuarios = (DefaultListModel) usuarios.getModel();

      for (String usr: usrL) {
        modelUsuarios.addElement(usr);
      }

      usuarios.setModel(modelUsuarios);
    }
    
  }
}
