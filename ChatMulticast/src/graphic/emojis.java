/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTML;

public class emojis extends JFrame{
    long time1;
    int WIDTH = 35;
    int HEIGHT = 35;
    int f=4;
    int c=17;
    int nemojis=f*c;
    emojis em;
    String selected="";
    ImageIcon imgi[];
	public emojis(Rectangle pos,JTextArea tx) {
            text=tx;
            em=this;
        panel = new JPanel( null );      
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel, BorderLayout.CENTER);
        this.setUndecorated(true);    
        this.setBounds(pos.x,pos.y,WIDTH*c+1,HEIGHT*f+1);
        
        imgi= new ImageIcon[nemojis];
        String sDirectorio = "emojis";
        File f = new File(sDirectorio);
        File[] ficheros = f.listFiles();
        if(f.exists() && ficheros.length>0){
                       
            for (int x=0;x<ficheros.length;x++){
              //System.out.println(x+".-"+ficheros[x].getAbsolutePath());
              imgi[x]=new ImageIcon(ficheros[x].getAbsolutePath());
              imgi[x].setDescription(ficheros[x].getAbsolutePath());
            }
        
        }else{
            System.out.println("error en el directorio");
            System.exit(0);
        }
            this.matriz();
	}
        public void setPos(Rectangle pos){
            this.setBounds(pos.x,pos.y+130,WIDTH*c+1,HEIGHT*f+1);
        }

	public void matriz() {
            int filas=f;
            int columnas=c;
    
        panel.removeAll();
        emoji [][] botones = new emoji[ filas ][ columnas ];
        for( int fila = 0 ; fila < filas; fila++ )
        {
            //Estando en la fila se recorrer las columnas
            for( int columna = 0 ; columna < columnas; columna++ )
            {
                //Se crea el boton y se agrega a las celda de la matriz
                botones[fila][columna] = new emoji( WIDTH* columna, HEIGHT * fila, WIDTH, HEIGHT ,em);
                
                //Se da el nombre en forma de coordenada enviando la fila y columna
                botones[fila][columna].setimagen(imgi[(fila*columnas)+columna]);
                //System.out.println("fila columna"+fila+columna+"img= "+imgi[fila+columna].getDescription());
           
                panel.add( botones[fila][columna] );
            }
        }
        
		panel.updateUI();
	}             
        public String getselect(){
            return selected;
        }
        public void setselect(String s){
            selected=s;
            text.append("<"+selected+">");
            
        }
        public ImageIcon[] getimages(){
            return imgi;
        }
        
	JPanel panel;
        JTextArea text;
    
}
