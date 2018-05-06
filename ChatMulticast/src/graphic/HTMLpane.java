/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphic;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTextPane;

public class HTMLpane extends JTextPane {

    public HTMLpane() {

        this.setContentType("text/html");

        //this.setText(this.getText().replace("<head>",String.format("<head><style> %s </style>", css)));
        this.setEditable(false);
        this.setBounds(5, 35, 500, 150);

    }

    /*  public HTMLpane(int i){
        this.setBounds(5,200, 300, 60);
        this.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                actionPerformed(evt);
            }
        });
    }

   public void actionPerformed(ActionEvent e) {
       String text = this.getText();
       text=text.replace(":(","<triste>");
       text=text.replace(":)","<sonrisa>");
       text=text.replace("<3","<corazon>");
       text=text.replace(":'(","<llorar3>");
       text=text.replace("(Y)","<like>");
       this.setText(text);
    }*/
    public String strtohtml(String s) {
        String html = s;
        /*for(int x=0;x<icons.length;x++){
            File fl = new File(icons[x].getDescription());
            String nombre = "<"+fl.getName().split(".png")[0]+">";
            html=html.replaceAll(nombre, "<img src=\"FILE:///"+fl.getAbsolutePath().replace("\\","\\\\")+"\" height=\"25\" width=\"25\">");
        }*/

        //System.out.println(""+html);
        return html;
    }

    public void append(String tx, String time, String user, boolean ismy) {

        String text = this.getText().split("<body>")[1].split("</body>")[0];
        String x = "<p> <b>" + user + "</b></font> (" + time + "): <br> " + tx + "</font></p>";
        if (ismy) {
            this.setText(text + String.format("<div  align=\"right\"> %s </div>", x));
        } else {
            this.setText(text + String.format("<div align=\"left\" > %s </div>", x));
        }

        //System.out.println(""+this.getText());
        this.setCaretPosition(this.getDocument().getLength());
    }

    public void appendImage(File f1, String time, String user, boolean ismy) throws IOException {
        Boolean valid = true;
        BufferedImage bi = null;
        Image image;
        try {
            image = ImageIO.read(f1);
            bi = ImageIO.read(f1);
            if (image == null) {
                valid = false;
                System.out.println("The file" + f1.getName() + "could not be opened , it is not an image");
            }
        } catch (IOException ex) {
            valid = false;
            System.out.println("The file" + f1.getName() + "could not be opened , an error occurred.");
        }
        if (valid) {
            int height = bi.getHeight();
            int width = bi.getWidth();
            System.out.println("Width: " + width);
            System.out.println("Height: " + height);
      
                System.out.println("Width: " + width);
                System.out.println("Height: " + height);
                
                String text = this.getText().split("<body>")[1].split("</body>")[0];
                if (ismy) {
                    this.setText(text + String.format("<div align=\"right\" > %s </div>", "<img src=\"FILE:///" + f1.getAbsolutePath().replace("\\", "\\\\") + "\" height=\"" + height + "\" width=\"" + width + "\">"));
                } else {
                    this.setText(text + String.format("<div align=\"left\" > %s </div>", "<img src=\"FILE:///" + f1.getAbsolutePath().replace("\\", "\\\\") + "\" height=\"" + height + "\" width=\"" + width + "\">"));
                }
            }
//            else{
//                String text = this.getText().split("<body>")[1].split("</body>")[0];
//                if (ismy) {
//                    this.setText(text + String.format("<div align=\"right\" > %s </div>", "<a href = \"" + f1.getAbsolutePath().replace("\\", "\\\\") + "\">" + f1.getName() + "</a>"));
//
//                } else {
//                    this.setText(text + String.format("<div align=\"left\" > %s </div>", "<a href = \"" + f1.getAbsolutePath().replace("\\", "\\\\") + "\">" + f1.getName() + "</a>"));
//                }
//            }
            
            this.setCaretPosition(this.getDocument().getLength());
        }

    public void seticons(ImageIcon[] img) {
        icons = img;
    }

    public String remplaceemo(String text) {
        //System.out.println(text);
        return text;
    }
    ImageIcon[] icons;
    
}
