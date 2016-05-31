import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by alicja on 25.05.16.
 */
@Entity
public class Post {
    @Id
    private Long id;

    private String userName;

    private String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
