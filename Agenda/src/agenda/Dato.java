package agenda;
import java.io.Serializable;

public class Dato implements Serializable{
    private String nombre;
    private boolean option;

    public Dato(String nombre, boolean option) {
        this.nombre = nombre;
        this.option = option;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean getOption() {
        return option;
    }
}