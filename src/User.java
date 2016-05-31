import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by alicja on 24.05.16.
 */
@Entity
public class User {
    @Id
    private int id;

    private String name;

    private byte[] hashedPassword;

    private byte[] salt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(byte[] hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
}
