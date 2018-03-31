package agenda;
import java.io.Serializable;

public class Contact implements Serializable{
    private String name;
    private String telephone;
    private String birthday;

    public Contact(String name, String telephone, String birthday) {
        this.name = name;
        this.telephone = telephone;
        this.birthday = birthday;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getBirthday() {
        return birthday;
    }
}