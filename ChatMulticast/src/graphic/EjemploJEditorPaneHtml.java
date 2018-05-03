/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphic;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;


public class EjemploJEditorPaneHtml
{
    /**
     * Crea un nuevo objeto EjemploJEditorPaneHtml.
     */
    public EjemploJEditorPaneHtml()
    {
        try
        {
          // Preparamos la ventana de ejemplo
            JFrame v = new JFrame("JEditorPane con HTML");
            JEditorPane editor = new JEditorPane();
            JScrollPane scroll = new JScrollPane(editor);
            v.getContentPane().add(scroll);
            v.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            
            // Marcamos el editor para que use HTML 
            editor.setContentType("text/html");
            
            // Insertamos un texto
            editor.setText(
              "<head><base href=\"file:d:/\"></head>"+
                "<b>hola</b><br>" + "<i>adios     &#9748</i><br>" +
                "<font face=\"roboto\">fuente arial</font><br>" +
                "<font face=\"arial\">fuente courier</font><br>" +
                "<font face=\"arial\" size=\"38\">fuente grande  &#9973</font><br>" +
                "<font color=\"red\">color rojo</font><br>" +
                "<img src=\"viejo.gif\"></img>");
            
            // Se visualiza la ventana
            v.pack();
            v.setVisible(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * main de prueba
     *
     * @param args Se ignoran.
     */
    public static void main(String[] args)
    {
        new EjemploJEditorPaneHtml();
    }
}
