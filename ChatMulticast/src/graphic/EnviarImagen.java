package graphic;

import Object_File.FileObj;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

public class EnviarImagen extends Thread {

    public static final String MCAST_ADDR = "230.0.0.1";//dir clase D valida, grupo al que nos vamos a unir
    public static final int MCAST_PORT = 9013;
    public static final int DGRAM_BUF_LEN = 512;
    public String usuario;

    public EnviarImagen(String usuario) {
        this.usuario = usuario;
    }

    public synchronized void run() {
        String msg = ""; // se cambiara para poner la ip de la maquina con lo siguiente
        InetAddress group = null;
        try {
            msg = InetAddress.getLocalHost().getHostAddress();
            group = InetAddress.getByName(MCAST_ADDR); //se trata de resolver dir multicast  		
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.exit(1);
        }

        /**
         * ******************inicia loop**************************
         */
        System.out.println("Selecciona un archvio:");
        JFileChooser jf = new JFileChooser();
        jf.requestFocus(true);
        jf.showOpenDialog(null);
        File file = jf.getSelectedFile();
        System.out.println(file.getName());
        
        try{
            BufferedImage bi = ImageIO.read(file);
            if(bi.getHeight() < 100 || bi.getWidth() <100){
                System.out.println("Imaga size not valid: minimum size shall be 100 x 00 px");
            }
            else{
                    try {
                    MulticastSocket socket = new MulticastSocket(MCAST_PORT);
                    socket.joinGroup(group); // se configura para escuchar el paquete

                    //FILE
                    byte[] bytesDelFile = null;
                    FileInputStream fileInputStream = null;
                    bytesDelFile = new byte[(int) file.length()];

                    //leer el archivo sobre el arreglo de bytes
                    fileInputStream = new FileInputStream(file);
                    fileInputStream.read(bytesDelFile);

                    int tbuf = 50000;
                    int numDatagramas = 0;

                    ByteArrayInputStream bais = new ByteArrayInputStream(bytesDelFile);
                    numDatagramas = (int) bytesDelFile.length / tbuf;
                    numDatagramas++;    //en el 1ro irà el nombre

                    if (bytesDelFile.length % tbuf > 0) {                                              // Se debe de enviar un datagrama más.
                        numDatagramas++;
                    }

                    FileObj obj;

                    for (int i = 0; i < numDatagramas; i++) {//for datagramas archivo
                        byte[] tmp = new byte[tbuf];
                        if (i == 0) {
                            String nombre = file.getName();
                            ByteArrayInputStream bais2 = new ByteArrayInputStream(nombre.getBytes());
                            int n = bais2.read(tmp);
                            obj = new FileObj(i + 1, numDatagramas, tmp, usuario);
                            System.out.println("Nombre del archivo: " + new String(obj.getB()));

                        } else {
                            int n = bais.read(tmp);
                            obj = new FileObj(i + 1, numDatagramas, tmp, usuario);
                        }
                        /* Envío el objeto */
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        ObjectOutputStream oos = new ObjectOutputStream(bos);
                        oos.writeObject(obj);
                        oos.flush();

                        byte[] buf = bos.toByteArray();

                        DatagramPacket packet = new DatagramPacket(buf, buf.length, group, MCAST_PORT);
                        System.out.println("===========================================");
                        System.out.println("packet lenght: " + packet.getLength());
                        System.out.println("Paquetes enviados: " + obj.getN());
                        System.out.println("Total de paquetes: " + obj.getTotal());
                        System.out.println("===========================================");
                        socket.send(packet);
                        oos.close();
                    }//for envio datagramas
                    //System.out.println("Enviando: " + msg+"  con un TTL= "+socket.getTimeToLive());
                    System.out.println("==Se envió el archivo completo==");
                    socket.close();
                } 
                catch (IOException e) {
                    e.printStackTrace();
                    System.exit(2);
                }
            }
        
        }
        catch (IOException ex) {
            System.out.println("The file" + file.getName() + "could not be opened , an error occurred.");
        }
       
        try {
            Thread.sleep(1000);
        } 
        catch (InterruptedException ie) {
        }
    }//run
}
