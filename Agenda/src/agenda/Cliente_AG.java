package agenda;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Cliente_AG {
    public static void main(String args[]){
        try{
            String dst = "127.0.0.1";
            int pto = 8888;
            
            Socket cl = new Socket(dst,pto);
            System.out.println("Stablished conexion... waiting for user...");
            
            int n  = JOptionPane.showConfirmDialog(null, "Would you like to search by fullname?","Search",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            String name = JOptionPane.showInputDialog(null,"Write the name/surname of the contact you want to search","Name",JOptionPane.INFORMATION_MESSAGE);
            
            Dato d;
            if(n == 0){d = new Dato(name,true);}
            else{d =new Dato(name,false);}
            
            ObjectOutputStream oos = new ObjectOutputStream(cl.getOutputStream());
            ObjectInputStream ois =  new ObjectInputStream(cl.getInputStream());
            
            System.out.println("Searching...");
            oos.writeObject(d);
            oos.flush();
            System.out.println("Recieving...");
            ArrayList<Contact>  results = (ArrayList<Contact>)ois.readObject();
            
            if(results.isEmpty()){
                System.out.println("No matches founded :(");
            }
            else{
                System.out.println("We found " + results.size() + " contacts with that name/surname");
                for(Contact c: results){
                    System.out.println("Name: " + c.getName());
                    System.out.println("Telephone: " + c.getTelephone());
                    System.out.println("Birthday: " +c.getBirthday() + "\n");
                }
            }   
            ois.close();
            oos.close();
            cl.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}