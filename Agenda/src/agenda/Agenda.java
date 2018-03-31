package agenda;
import java.util.ArrayList;

public class Agenda {
    private  ArrayList<Contact> contacts;
    
    public Agenda(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }
}