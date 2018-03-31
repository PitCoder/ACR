package agenda;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Servidor_AG {
    public static void main(String args[]) throws IOException, ClassNotFoundException{
        String fileName = "Agenda.txt";
        String line = "";
        try{
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            ArrayList<Contact> contacts = new ArrayList<>();
            while((line = br.readLine()) != null){
                String[] Info = line.split(",");
                Contact c = new Contact(Info[0],Info[2],Info[1]);
                contacts.add(c);
            }  
            Agenda a = new Agenda(contacts);
            System.out.println("Agenda loaded sucessfully...");
            System.out.println("Initiating service...");
            
            int pto = 8888;
            ServerSocket s = new ServerSocket(pto);
            s.setReuseAddress(true);

            while(true){
                Socket cl = s.accept();
                System.out.println("Client conected from " + cl.getInetAddress() + " :" + cl.getPort());

                ObjectOutputStream oos = new ObjectOutputStream(cl.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(cl.getInputStream());

                System.out.println("Search requested...");
                Dato d = (Dato)ois.readObject();
                
                ArrayList<Contact> coincidences = new ArrayList<>();
                
                if(d.getOption()){//Search Fullname
                    for(Contact c: a.getContacts()){
                        if(c.getName().equals(d.getNombre()))
                            coincidences.add(c);
                    }
                }
                else{//Search for coindences in name or surname
                    for(Contact c: a.getContacts()){
                        if(c.getName().contains(d.getNombre()))
                            coincidences.add(c);
                    }
                }
                
                System.out.println("Sending results...");
                oos.writeObject(coincidences);
                oos.flush();
                oos.close();
                ois.close();
                cl.close();
            }
        }
        catch(FileNotFoundException ex){
            ex.printStackTrace();
        }
    }
}