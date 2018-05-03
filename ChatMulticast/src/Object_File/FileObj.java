package Object_File;

import java.io.Serializable;
import java.util.Arrays;

public class FileObj implements Serializable{
    String Usuario="";
    int n;
    int total;
    byte[] msj;
    
    public FileObj(int n, int t, byte[] b, String usr){
        this.n = n;
        this.total = t;
        this.msj = Arrays.copyOf(b, b.length);
        this.Usuario=usr;
    }
    public int getN(){
        return this.n;
    }
    public int getTotal(){
        return this.total;
    }
    public String getUsuario(){
        return this.Usuario;
    }
    public byte[] getB(){
        return this.msj;
    }
    public void setB(byte[] msj) {
        this.msj = msj;
    }
}
