import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by alicja on 24.05.16.
 */
@Entity
public class User {
    @Id
    private Long id;

    private String name;

    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
