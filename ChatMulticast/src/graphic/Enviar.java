package graphic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Enviar extends Thread{
    HTMLpane mensajeria;
    MulticastSocket msE;
    InetAddress gpo;
    int ptE;
    Mensaje M;
    String privado="";
    
    Enviar(String msg, HTMLpane mensajeria, MulticastSocket msE, InetAddress gpo, int ptE, Mensaje M){
        super(msg);
        this.msE = msE;
        this.gpo = gpo;
        this.ptE = ptE;
        this.M = M;
    }
    
    Enviar(String msg, MulticastSocket msE, InetAddress gpo, int ptE, Mensaje M){
        super(msg);
        this.msE = msE;
        this.gpo = gpo;
        this.ptE = ptE;
        this.M = M;
    }
    
    @Override
    public void run(){
        try {
            enviar();
            Thread.sleep(100);
        } catch (InterruptedException e) {}
    }
    
    public void enviar(){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(M);
            oos.flush();
            byte []msj = baos.toByteArray();
            DatagramPacket p = new DatagramPacket(msj,msj.length,gpo,ptE);
            msE.send(p);
        } catch (IOException e) {}
    }
    
}
