package graphic;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Scanner;

public class Chat {
    static String Usuario;
    static MulticastSocket ms;
    static int pt = 8001;
    static String dir = "235.23.13.21";
    static InetAddress gpo = null;
    
    public static void iniciarRecibir(){
        //Thread recibir = new Recibir("RecibirPublico",Usuario,ms,gpo,pt);
        //recibir.start();
        
    }
    
    public static void main(String[] args) {
        try {
            ms = new MulticastSocket(pt);
            ms.setReuseAddress(true);
            ms.setTimeToLive(255);
            try {
                gpo = InetAddress.getByName(dir);
            } catch (UnknownHostException e) {
                System.out.println("Direccion Multicast no valida");
                ms.close();
                System.exit(1);
            }
            ms.joinGroup(gpo);
            System.out.println("Servicio iniciado y unido al grupo: "+dir);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Ingrese Usuario:");
        String usua="";
        Scanner entrada = new Scanner (System.in);
        usua= entrada.nextLine();
        Usuario=usua;
        for(;;){
            iniciarRecibir();
            
            Calendar calendario = Calendar.getInstance();
            calendario.getTime();
            int horaH =calendario.get(Calendar.HOUR_OF_DAY);
            int min = calendario.get(Calendar.MINUTE);
            int sec = calendario.get(Calendar.SECOND);
            String hora = String.valueOf(horaH+":"+min+":"+sec);
            System.out.println("Escriba un mensaje al chat:");
            String mensaje = "";
            Scanner entradaEscaner = new Scanner (System.in);
            mensaje = entradaEscaner.nextLine();
            Mensaje M = new Mensaje(Usuario,hora,mensaje,"publico");
//            Thread enviar = new Enviar("Enviar",ms,gpo,pt,M);
  //          enviar.start();
            
        }
    }
}
