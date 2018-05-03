/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphic;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.regex.Pattern;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class emoji extends JButton implements ActionListener{
    String desc;
    String nombre;
    emojis emo;
    public emoji( int pos_x, int pos_y, int ancho, int alto ,emojis em){ 
        emo=em;
        //Se coloca el boton en un lugar y se le da un tamanio
        setBounds(pos_x, pos_y, ancho, alto);
        //Se agrega el action listener en este caso la misma clase
        addActionListener( this );
        //this.setBorderPainted(false);
        this.setOpaque(false);
        this.setContentAreaFilled(false);
    }
    public void setimagen(ImageIcon img){
        Icon icono = new ImageIcon(img.getImage().getScaledInstance(this.getWidth(),this.getHeight(), Image.SCALE_DEFAULT));
        this.setIcon(icono);
        desc=img.getDescription();
        //System.out.println(""+desc);
        //String part[] = desc.split(Pattern.quote(File.separator));
        //nombre= part[part.length-1];
        //nombre=p[0];
        nombre = desc;
       
       File fl = new File(desc);
       nombre = fl.getName().split(".png")[0];
       
 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       
        System.out.println(""+nombre);
        emo.setselect(nombre);
        //emo.dispose();
        emo.setVisible(false);
       
    }
    
}
