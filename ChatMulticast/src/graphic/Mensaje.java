package graphic;

import graphic.Chat.*;
import java.io.Serializable;

public class Mensaje implements Serializable{
    String Usuario="";
    String hora="";
    String mensaje="";
    String destinatario="";
    String tipo="";

    //Mensaje para todos
    public Mensaje(String Usuario, String hora, String mensaje, String tipo) {
        this.Usuario = Usuario;
        this.hora = hora;
        this.mensaje = mensaje;
        this.tipo = tipo;
    }
    
    //Mensaje privado
    public Mensaje(String Usuario, String hora, String mensaje, String destinatario, String tipo) {
        this.Usuario = Usuario;
        this.hora = hora;
        this.mensaje = mensaje;
        this.destinatario = destinatario;
        this.tipo = tipo;
    }

    public String getUsuario() {
        return Usuario;
    }

    public String getHora() {
        return hora;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public String getTipo() {
        return tipo;
    }
    
}
