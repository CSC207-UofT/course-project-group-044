package Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class User {


    @Id
    private final String name;

    @Column
    private String password;

    public User(String name) {

        this.name = name;

    }

    public User() {
        this.name = "";
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getId() {
        return name;
    }
}
