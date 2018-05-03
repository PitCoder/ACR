package graphic;

import Object_File.FileObj;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.rmi.UnexpectedException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JProgressBar;

public class RecibirImagen extends Thread {

    public static final String MCAST_ADDR = "230.0.0.1"; //dir clase D valida, grupo al que nos vamos a unir
    public static final int MCAST_PORT = 9013;//puerto multicast
    public static final int DGRAM_BUF_LEN = 512; //tamaño del buffer
    public JProgressBar avance;
    public HTMLpane mensajeria;
    public String Usuario;

    public RecibirImagen(JProgressBar avn, HTMLpane mensajeria, String usr) {
        this.avance = avn;
        this.mensajeria = mensajeria;
        this.Usuario=usr;
    }

    public void run() {
        InetAddress group = null;
        try {
            group = InetAddress.getByName(MCAST_ADDR);//intenta resolver la direccion
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.exit(1);
        }
        boolean salta = true;
        try {

            MulticastSocket socket = new MulticastSocket(MCAST_PORT); //socket tipo multicast
            socket.joinGroup(group);//se une al grupo
            //int cd=0;
            File myFile = null;
            FileOutputStream stream = null;

            System.out.println("Servicio iniciado. Esperando el/los archivos");
            int tbuf = 65500;
            FileObj objeto = null;
            while (salta) {//while infinito
                try {

                    DatagramPacket p = new DatagramPacket(new byte[tbuf], tbuf);
                    System.out.println("Esperando archivo...");

                    //byte[] buf = new byte[DGRAM_BUF_LEN];//crea arreglo de bytes 
                    //DatagramPacket recv = new DatagramPacket(buf,buf.length);//crea el datagram packet a recibir
                    socket.receive(p);
                    System.out.println("Archivo recibido:");
                    System.out.println("Host remoto: " + p.getAddress());
                    System.out.println("Puerto: " + p.getPort());
                    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(p.getData()));
                    objeto = (FileObj) ois.readObject();
                    ois.close();
                } catch (EOFException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(RecibirImagen.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (objeto.getN() == 1) {           //en el primer paquete viajará el nombre

                    String msg = new String(objeto.getB(), 0, objeto.getB().length);
                    msg = msg.trim();                 //dejar la cadena limpia, sin caracteres no validos para nombre de un File

                    myFile = new File(msg);
                    stream = new FileOutputStream(myFile);

                    System.out.println(objeto.getN());
                    System.out.println(objeto.getTotal());

                } else {
                    System.out.println(objeto.getN());
                    System.out.println(objeto.getTotal());
                    stream.write(objeto.getB(), 0, objeto.getB().length);
                    stream.flush();
                    avance.setValue((objeto.getN() * 100) / objeto.getTotal());
                    System.out.println("prgb: " + avance.getValue());
                    if (objeto.getN() == objeto.getTotal()) {
                        stream.close();
                        System.out.println("Archivo recibido completamente");
                        System.out.println("File enviado por " + objeto.getUsuario());
                        //salta=false;
                        

                        this.mensajeria.appendImage(myFile, myFile.getPath(), objeto.getUsuario(), this.Usuario.equals(objeto.getUsuario()));

                    }
                }
            }
        } catch (UnexpectedException e) {
            e.printStackTrace();
            //System.exit(2);
        } catch (IOException ex) {
            Logger.getLogger(RecibirImagen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//run
}
